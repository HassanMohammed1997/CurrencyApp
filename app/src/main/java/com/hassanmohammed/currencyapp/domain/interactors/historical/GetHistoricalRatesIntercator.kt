package com.hassanmohammed.currencyapp.domain.interactors.historical

import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.models.HistoricalRate
import kotlinx.coroutines.flow.Flow

interface GetHistoricalRatesIntercator {
    operator fun invoke(base: String, otherCurrency: String): Flow<Resource<List<HistoricalRate>>>
}