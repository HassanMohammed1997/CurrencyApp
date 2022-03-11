package com.hassanmohammed.currencyapp.data

import com.hassanmohammed.currencyapp.data.remote.CurrencyService
import com.hassanmohammed.currencyapp.data.remote.dto.currencyconverter.CurrencyConverterDto
import com.hassanmohammed.currencyapp.data.remote.dto.historical.HistoricalRateDto

class MainRepositoryImpl(
    private val apiService: CurrencyService
) : MainRepository {
    override suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbol: String
    ): HistoricalRateDto = apiService.getHistoricalRates(date, base, symbol)

    override suspend fun convert(from: String, to: String, amount: String): CurrencyConverterDto =
        apiService.convert(from, to, amount)
}