package com.hassanmohammed.currencyapp.domain.interactors.historical

import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.models.HistoricalRate
import kotlinx.coroutines.flow.Flow

interface GetHistoricalRatesIntercator {
    operator fun invoke(date: String, base: String, symbol: String): Flow<Resource<List<HistoricalRate>>>
}