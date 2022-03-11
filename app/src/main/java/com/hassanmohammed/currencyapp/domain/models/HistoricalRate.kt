package com.hassanmohammed.currencyapp.domain.models

data class HistoricalRate(
    val date: String = "",
    val rates: Map<String, Float> = emptyMap()
)