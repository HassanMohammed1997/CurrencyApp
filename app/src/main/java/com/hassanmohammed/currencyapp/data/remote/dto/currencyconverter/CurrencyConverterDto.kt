package com.hassanmohammed.currencyapp.data.remote.dto.currencyconverter


import com.google.gson.annotations.SerializedName
import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter

data class CurrencyConverterDto(
    @SerializedName("base_currency_code")
    val baseCurrencyCode: String = "",
    @SerializedName("base_currency_name")
    val baseCurrencyName: String = "",
    val amount: String = "",
    @SerializedName("updated_date")
    val updatedDate: String = "",
    val rates: Map<String, RatesDto> = emptyMap(),
    val status: String = ""
) {
    fun toCurrencyConverter() =
        CurrencyConverter(
            rates.values.first().currencyName,
            rates.values.first().rateForAmount.toFloat()
        )
}