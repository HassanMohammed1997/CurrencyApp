package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.atIndex
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.setItems
import com.hassanmohammed.currencyapp.utils.fragmentViewBinding
import com.hassanmohammed.currencyapp.utils.getCountries
import com.hassanmohammed.currencyapp.utils.getCurrency
import com.hassanmohammed.currencyapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(R.layout.fragment_currency_converter) {
    private val binding by fragmentViewBinding(FragmentCurrencyConverterBinding::bind)
    private val viewModel by viewModels<CurrencyConvertViewModel>()
    private var baseCurrency: String = ""
    private var otherCurrency: String = ""
    private var baseCurrencyIdx: Int = 0
    private var otherCurrencyIdx: Int = 0
    private var amount: String = "1"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.currencyViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
            baseCurrency = getCurrency(it.toString())
        }

        binding.otherCurrencySelector.doAfterTextChanged {
            otherCurrency = getCurrency(it.toString())
        }

        binding.amountEt.doAfterTextChanged {
            amount = it.toString()
        }

        binding.swapBtn.setOnClickListener { swapSpinnersValues() }

        binding.convertBtn.setOnClickListener {
            hideKeyboard()
            viewModel.convert(baseCurrency, otherCurrency, amount)
        }
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