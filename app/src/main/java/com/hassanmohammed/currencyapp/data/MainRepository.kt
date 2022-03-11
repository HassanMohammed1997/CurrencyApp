package com.hassanmohammed.currencyapp.data

import com.hassanmohammed.currencyapp.data.remote.dto.historical.HistoricalRateDto

interface MainRepository {
    suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbol: String
    ) : HistoricalRateDto
}