package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.interactors.currencyconverter.ConvertCurrencyInteractor
import com.hassanmohammed.currencyapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CurrencyConvertViewMode"

@HiltViewModel
class CurrencyConvertViewModel @Inject constructor(
    private val convertCurrencyInteractor: ConvertCurrencyInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<CurrencyConverterUiState> =
        MutableStateFlow(CurrencyConverterUiState())
    val uiState: StateFlow<CurrencyConverterUiState> = _uiState

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun convert(from: String, to: String, amount: String) = viewModelScope.launch {
        convertCurrencyInteractor(from, to, amount)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> _uiState.value = uiState.value.copy(
                        isLoading = true
                    )
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
                    is Resource.Success -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            data = result.data
                        )
                    }
                }

            }.launchIn(this)
    }

}