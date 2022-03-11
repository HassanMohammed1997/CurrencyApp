package com.hassanmohammed.currencyapp.utils

import androidx.annotation.StringRes

sealed class UiEvent {
    data class ShowSnackBar(val message: String?, @StringRes val messageRes: Int? = null) :
        UiEvent()
}