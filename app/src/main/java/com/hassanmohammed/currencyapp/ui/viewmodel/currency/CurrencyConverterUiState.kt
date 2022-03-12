package com.hassanmohammed.currencyapp.ui.viewmodel.currency

import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter

data class CurrencyConverterUiState(
    var data: CurrencyConverter? = null,
    var isLoading: Boolean = false
) {
}