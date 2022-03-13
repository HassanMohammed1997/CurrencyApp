package com.hassanmohammed.currencyapp.domain.interactors.currencyconverter

import com.hassanmohammed.currencyapp.data.MainRepository
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConvertBaseCurrencyIntoMultipleCurrenciesInteractorImpl @Inject constructor(
    private val repository: MainRepository
) :
    ConvertBaseCurrencyIntoMultipleCurrenciesInteractor {
    override fun invoke(
        base: String,
        amount: String,
        currencies: Array<String>
    ): Flow<Resource<List<CurrencyConverter>>> = flow {
        var finalAmount = amount
        if (finalAmount.isEmpty() || finalAmount == "0")
            finalAmount = "1"
        emit(Resource.Loading())

        try {
            val allHistoricalRates = mutableListOf<CurrencyConverter>()
            coroutineScope {
                currencies.forEach { currency ->
                    val api = async { repository.convert(base, currency, finalAmount) }
                    allHistoricalRates.add(api.await().toCurrencyConverter())
                }
            }
            emit(Resource.Success(allHistoricalRates.toList()))
        } catch (e: HttpException) {
            emit(Resource.ServerError(e.message(), e.code()))
        } catch (e: IOException) {
            emit(Resource.NetworkError())
        }

    }
}