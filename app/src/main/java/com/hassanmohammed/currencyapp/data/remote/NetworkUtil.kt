package com.hassanmohammed.currencyapp.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T> safeApiCall(
    call: suspend () -> T
): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    try {
        val response = call()
        emit(Resource.Success(response))
    } catch (e: HttpException) {
        emit(
            Resource.ServerError(
                message = e.localizedMessage ?: "Something went error",
                code = e.code()
            )
        )
    } catch (e: IOException) {
        emit(Resource.NetworkError())
    }
}