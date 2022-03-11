package com.hassanmohammed.currencyapp.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter

object BindingAdapterUtil {
    @BindingAdapter("app:setItems")
    @JvmStatic
    fun AutoCompleteTextView.setItems(items: List<Any>?) {
        items?.let {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, it)
            setAdapter(adapter)
        }
    }

    @BindingAdapter("app:setDefaultIndex")
    @JvmStatic
    fun AutoCompleteTextView.atIndex(index: Int) {
        adapter?.let {
            val itemAtPosition = it.getItem(index) as String
            this.setText(itemAtPosition, false)
        }
    }
}