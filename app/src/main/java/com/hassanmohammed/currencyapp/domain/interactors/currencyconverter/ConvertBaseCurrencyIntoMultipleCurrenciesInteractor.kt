package com.hassanmohammed.currencyapp.domain.interactors.currencyconverter

import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter
import kotlinx.coroutines.flow.Flow

interface ConvertBaseCurrencyIntoMultipleCurrenciesInteractor {
    operator fun invoke(
        base: String,
        amount: String,
        currencies: Array<String>
    ): Flow<Resource<List<CurrencyConverter>>>
}