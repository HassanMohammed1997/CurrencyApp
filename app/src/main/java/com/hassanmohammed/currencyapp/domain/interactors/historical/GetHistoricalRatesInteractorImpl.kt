package com.hassanmohammed.currencyapp.domain.interactors.historical

import com.hassanmohammed.currencyapp.data.MainRepository
import com.hassanmohammed.currencyapp.data.remote.Resource
import com.hassanmohammed.currencyapp.domain.models.HistoricalRate
import com.hassanmohammed.currencyapp.utils.fromNowPast
import com.hassanmohammed.currencyapp.utils.toFormattedDate
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHistoricalRatesInteractorImpl @Inject constructor(
    private val repository: MainRepository
) : GetHistoricalRatesIntercator {
    override fun invoke(
        base: String,
        otherCurrency: String
    ): Flow<Resource<List<HistoricalRate>>> = flow {
        emit(Resource.Loading())
        try {
            coroutineScope {
                val historicalDay1 =
                    async { repository.getHistoricalRates(1.fromNowPast.toFormattedDate(), base, otherCurrency) }
                val historicalDay2 =
                    async {
                        repository.getHistoricalRates(
                            (2.fromNowPast.toFormattedDate()),
                            base,
                            otherCurrency
                        )
                    }
                val historicalDay3 =
                    async {
                        repository.getHistoricalRates(
                            3.fromNowPast.toFormattedDate(),
                            base,
                            otherCurrency
                        )
                    }
                val allHistoricalRates = mutableListOf<HistoricalRate>()
                allHistoricalRates.add(historicalDay1.await().toHistoricalRate())
                allHistoricalRates.add(historicalDay2.await().toHistoricalRate())
                allHistoricalRates.add(historicalDay3.await().toHistoricalRate())
                emit(
                    Resource.Success(
                        allHistoricalRates.toList()
                    )
                )
            }
        } catch (e: HttpException) {
            emit(Resource.ServerError(message = e.message(), code = e.code()))
        } catch (e: IOException) {
            emit(Resource.NetworkError())
        }

    }
}