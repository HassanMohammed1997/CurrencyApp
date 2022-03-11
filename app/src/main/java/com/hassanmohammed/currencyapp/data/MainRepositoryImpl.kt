package com.hassanmohammed.currencyapp.data

import com.hassanmohammed.currencyapp.data.remote.CurrencyService
import com.hassanmohammed.currencyapp.data.remote.dto.historical.HistoricalRateDto

class MainRepositoryImpl(
    private val apiService: CurrencyService
) : MainRepository {
    override suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbol: String
    ): HistoricalRateDto = apiService.getHistoricalRates(date, base, symbol)
}