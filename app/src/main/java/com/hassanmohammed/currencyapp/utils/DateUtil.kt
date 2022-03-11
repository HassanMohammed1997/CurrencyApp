package com.hassanmohammed.currencyapp.utils

import java.text.SimpleDateFormat
import java.util.*

val Int.fromNowPast: Long
    get() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -this)
        return cal.timeInMillis
    }

fun Long.toFormattedDate(
    format: String = Constants.SERVER_DATE_FORMAT,
    locale: Locale = Locale.getDefault()
): String {
    val sf = SimpleDateFormat(format, locale)
    return sf.format(this).orEmpty()
}

fun now() = System.currentTimeMillis()