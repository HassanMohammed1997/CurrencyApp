package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp.ui.fragments.historical.HistoricalRateViewModel
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.atIndex
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.setItems
import com.hassanmohammed.currencyapp.utils.fragmentViewBinding
import com.hassanmohammed.currencyapp.utils.getCountries
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(R.layout.fragment_currency_converter) {
    private val binding by fragmentViewBinding(FragmentCurrencyConverterBinding::bind)
    private val viewModel by viewModels<HistoricalRateViewModel>()
    private var baseCurrency: String = ""
    private var otherCurrency: String = ""
    private var baseCurrencyIdx: Int = 0
    private var otherCurrencyIdx: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCountriesInSpinners()
        setListenerForViews()
    }

    private fun setListenerForViews() {
        binding.baseSelector.setOnItemClickListener { adapterView, view, position, l ->
            baseCurrencyIdx = position
        }

        binding.otherCurrencySelector.setOnItemClickListener { adapterView, view, position, l ->
            otherCurrencyIdx = position
        }

        binding.baseSelector.doAfterTextChanged {
            baseCurrency = it.toString()
        }

        binding.otherCurrencySelector.doAfterTextChanged {
            otherCurrency = it.toString()
        }

        binding.swapBtn.setOnClickListener { swapSpinnersValues() }
    }

    private fun setCountriesInSpinners() {
        binding.baseSelector.setItems(getCountries())
        binding.otherCurrencySelector.setItems(getCountries())
    }

    private fun swapSpinnersValues() {
        swapIndices()
        binding.baseSelector.atIndex(baseCurrencyIdx)
        binding.otherCurrencySelector.atIndex(otherCurrencyIdx)
    }

    private fun swapIndices() {
        val temp = baseCurrencyIdx
        baseCurrencyIdx = otherCurrencyIdx
        otherCurrencyIdx = temp
    }
}