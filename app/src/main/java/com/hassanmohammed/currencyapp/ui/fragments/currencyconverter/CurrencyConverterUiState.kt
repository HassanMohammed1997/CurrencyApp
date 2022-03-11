package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter

data class CurrencyConverterUiState(
    var data: CurrencyConverter? = null,
    var isLoading: Boolean = false
) {
}