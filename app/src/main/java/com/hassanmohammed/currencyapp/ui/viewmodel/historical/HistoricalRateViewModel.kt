package com.hassanmohammed.currencyapp.ui.viewmodel.historical

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.interactors.historical.GetHistoricalRatesIntercator
import com.hassanmohammed.currencyapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HistoricalRateViewModel"

@HiltViewModel
class HistoricalRateViewModel @Inject constructor(
    private val getHistoricalRatesIntercator: GetHistoricalRatesIntercator
) : ViewModel() {
    private val _uiState: MutableStateFlow<HistoricalRatesUiState> =
        MutableStateFlow(HistoricalRatesUiState())
    val uiState: StateFlow<HistoricalRatesUiState> = _uiState

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun getHistoricalRates(base: String, symbol: String) = viewModelScope.launch {
        getHistoricalRatesIntercator(base, symbol)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.NetworkError -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false
                        )
                        _uiEvent.emit(UiEvent.ShowSnackBar(result.message, result.messageRes))
                    }
                    is Resource.ServerError -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false
                        )
                        _uiEvent.emit(UiEvent.ShowSnackBar(result.message, result.messageRes))
                    }
                    is Resource.Success -> _uiState.value = uiState.value.copy(
                        data = result.data,
                        isLoading = false
                    )
                }
            }
            .launchIn(this)
    }
}