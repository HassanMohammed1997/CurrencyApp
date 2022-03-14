package com.hassanmohammed.currencyapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hassanmohammed.currencyapp.databinding.ListItemHistoricalRatesBinding
import com.hassanmohammed.currencyapp.domain.models.HistoricalRate
import javax.inject.Inject

class HistoricalRateRecyclerAdapter @Inject constructor() :
    ListAdapter<HistoricalRate, HistoricalRateRecyclerAdapter.HistoricalRateViewHolder>(
        HistoricalRateDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalRateViewHolder =
        HistoricalRateViewHolder.get(parent)

    override fun onBindViewHolder(holder: HistoricalRateViewHolder, position: Int) =
        holder.bind(getItem(position))

    class HistoricalRateViewHolder(private val binding: ListItemHistoricalRatesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun get(parent: ViewGroup): HistoricalRateViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemHistoricalRatesBinding.inflate(inflater, parent, false)
                return HistoricalRateViewHolder(binding)
            }
        }

        fun bind(item: HistoricalRate) {
            binding.historicalRate = item
            binding.executePendingBindings()
        }
    }
}