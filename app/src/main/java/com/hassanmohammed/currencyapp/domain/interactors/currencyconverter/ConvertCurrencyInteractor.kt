package com.hassanmohammed.currencyapp.domain.interactors.currencyconverter

import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter
import kotlinx.coroutines.flow.Flow

interface ConvertCurrencyInteractor {
    operator fun invoke(from: String, to: String, amount: String): Flow<Resource<CurrencyConverter>>
}