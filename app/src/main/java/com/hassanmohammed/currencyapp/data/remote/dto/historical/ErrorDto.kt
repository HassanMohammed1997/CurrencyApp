package com.hassanmohammed.currencyapp.data.remote.dto.historical

data class ErrorDto(
    val code: Int?,
    val type: String
) {

    val message: String
    get() = type.split("_").joinToString(" ", transform = String::capitalize)

}
