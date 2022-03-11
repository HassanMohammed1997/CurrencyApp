package com.hassanmohammed.currencyapp.di

import com.hassanmohammed.currencyapp.BuildConfig
import com.hassanmohammed.currencyapp.data.remote.CurrencyService
import com.hassanmohammed.currencyapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor() : HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
    @Provides
    @Singleton
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(Constants.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.FIXER_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }
}