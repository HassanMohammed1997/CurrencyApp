package com.hassanmohammed.currencyapp.presentation.viewmodel.historical

import com.hassanmohammed.currencyapp.domain.models.HistoricalRate

data class HistoricalRatesUiState(
    val data: List<HistoricalRate>? = emptyList(),
    val isLoading: Boolean = false
) {
    val errorMessage: String get() = data?.firstOrNull()?.errorMessage ?: ""
}
