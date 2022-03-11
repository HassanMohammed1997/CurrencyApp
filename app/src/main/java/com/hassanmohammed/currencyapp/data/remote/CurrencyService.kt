package com.hassanmohammed.currencyapp.data.remote

import com.hassanmohammed.currencyapp.BuildConfig
import com.hassanmohammed.currencyapp.data.remote.dto.currencyconverter.CurrencyConverterDto
import com.hassanmohammed.currencyapp.data.remote.dto.historical.HistoricalRateDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyService {
    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String,
        @Query("access_key") apiKey: String = BuildConfig.FIXER_API_KEY,
    ): HistoricalRateDto

    @GET(BuildConfig.CONVERTER_API_URL)
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String,
        @Query("api_key") apiKey: String = BuildConfig.CONVERTER_API_KEY,
    ) : CurrencyConverterDto
}