package com.hassanmohammed.currencyapp.domain.interactors.historical

import com.hassanmohammed.currencyapp.data.MainRepository
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.data.remote.safeApiCall
import com.hassanmohammed.currencyapp.domain.models.HistoricalRate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoricalRatesInteractorImpl @Inject constructor(
    private val repository: MainRepository
) : GetHistoricalRatesIntercator {
    override fun invoke(
        date: String,
        base: String,
        symbol: String
    ): Flow<Resource<HistoricalRate>> =
        safeApiCall { repository.getHistoricalRates(date, base, symbol).toHistoricalRate() }
}