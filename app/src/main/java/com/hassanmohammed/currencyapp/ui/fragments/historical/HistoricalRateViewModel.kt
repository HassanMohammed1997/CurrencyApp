package com.hassanmohammed.currencyapp.ui.fragments.historical

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.interactors.historical.GetHistoricalRatesIntercator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HistoricalRateViewModel"

@HiltViewModel
class HistoricalRateViewModel @Inject constructor(
    private val getHistoricalRatesIntercator: GetHistoricalRatesIntercator
) : ViewModel() {

    fun getHistoricalRates(date: String, base: String, symbol: String) = viewModelScope.launch {
        getHistoricalRatesIntercator(date, base, symbol)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> Log.d(TAG, "getHistoricalRates: Loading...")
                    is Resource.NetworkError -> Log.e(TAG, "getHistoricalRates: ${result.message}")
                    is Resource.ServerError -> Log.e(TAG, "getHistoricalRates: ${result.message}")
                    is Resource.Success -> Log.d(TAG, "getHistoricalRates: ${result.data}")
                }
            }
            .launchIn(this)
    }
}