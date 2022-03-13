package com.hassanmohammed.currencyapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hassanmohammed.currencyapp.databinding.ListItemCurrencyBinding
import com.hassanmohammed.currencyapp.domain.models.CurrencyConverter
import javax.inject.Inject

class CurrenciesConversionRecyclerAdapter @Inject constructor() :
    ListAdapter<CurrencyConverter, CurrenciesConversionRecyclerAdapter.CurrencyViewHolder>(
        CurrencyConversionDiffUtil()
    ) {
    class CurrencyViewHolder(private val binding: ListItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun get(viewParent: ViewGroup): CurrencyViewHolder {
                val inflater = LayoutInflater.from(viewParent.context)
                val binding = ListItemCurrencyBinding.inflate(inflater, viewParent, false)
                return CurrencyViewHolder(binding)
            }
        }

        fun bind(item: CurrencyConverter) {
            binding.currencyConverter = item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder.get(parent)

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.bind(getItem(position))
}