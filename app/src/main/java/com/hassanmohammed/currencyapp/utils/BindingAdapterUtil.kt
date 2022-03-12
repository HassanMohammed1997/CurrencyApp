package com.hassanmohammed.currencyapp.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

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

    @BindingAdapter("app:visibleGone")
    @JvmStatic
    fun View.visibleGone(gone: Boolean){
        isGone = gone
    }
}