package com.hassanmohammed.currencyapp.domain.interactors.currencyconverter

import com.hassanmohammed.currencyapp.data.MainRepository
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.data.remote.safeApiCall
import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConvertCurrencyInteractorImpl @Inject constructor(
    private val repository: MainRepository
) : ConvertCurrencyInteractor {
    override fun invoke(
        from: String,
        to: String,
        amount: String
    ): Flow<Resource<CurrencyConverter>> =
        safeApiCall { repository.convert(from, to, amount).toCurrencyConverter() }
}