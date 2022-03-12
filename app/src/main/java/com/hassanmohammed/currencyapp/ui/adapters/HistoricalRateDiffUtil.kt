package com.hassanmohammed.currencyapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hassanmohammed.currencyapp.domain.models.HistoricalRate

class HistoricalRateDiffUtil : DiffUtil.ItemCallback<HistoricalRate>() {
    override fun areItemsTheSame(oldItem: HistoricalRate, newItem: HistoricalRate): Boolean =
        oldItem.date == newItem.date && oldItem.rate == oldItem.rate

    override fun areContentsTheSame(oldItem: HistoricalRate, newItem: HistoricalRate): Boolean =
        oldItem == newItem
}