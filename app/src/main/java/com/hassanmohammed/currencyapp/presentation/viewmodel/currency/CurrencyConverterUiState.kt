package com.hassanmohammed.currencyapp.presentation.viewmodel.currency

import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter

data class CurrencyConverterUiState(
    var data: CurrencyConverter? = null,
    var currenciesRateConverters: List<CurrencyConverter>? = null,
    var isLoading: Boolean = false
) {
}