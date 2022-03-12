package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp.utils.*
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.atIndex
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.setItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(R.layout.fragment_currency_converter) {
    private val binding by fragmentViewBinding(FragmentCurrencyConverterBinding::bind)
    private val viewModel by viewModels<CurrencyConvertViewModel>()
    private var fromCurrencyCode: String = ""
    private var toCurrencyCode: String = ""
    private var fromCurrencyIdx: Int = 0
    private var toCurrencyIdx: Int = 0
    private var amount: String = "1"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.currencyViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setCountriesInSpinners()
        setListenerForViews()

        //TODO Handle server error
    }

    private fun setListenerForViews() {
        binding.baseSelector.setOnItemClickListener { adapterView, view, position, l ->
            fromCurrencyIdx = position
        }

        binding.otherCurrencySelector.setOnItemClickListener { adapterView, view, position, l ->
            toCurrencyIdx = position
        }

        binding.baseSelector.doAfterTextChanged {
            fromCurrencyCode = getCurrency(it.toString())
        }

        binding.otherCurrencySelector.doAfterTextChanged {
            toCurrencyCode = getCurrency(it.toString())
        }

        binding.amountEt.doAfterTextChanged {
            amount = it.toString()
        }

        binding.swapBtn.setOnClickListener { swapSpinnersValues() }

        binding.convertBtn.setOnClickListener {
            hideKeyboard()
            viewModel.convert(fromCurrencyCode, toCurrencyCode, amount)
        }

        binding.detailsBtn.setOnClickListener { navigateToHistoricalRatesScreen() }
    }

    private fun setCountriesInSpinners() {
        binding.baseSelector.setItems(getCountries())
        binding.otherCurrencySelector.setItems(getCountries())
    }

    private fun swapSpinnersValues() {
        swapIndices()
        binding.baseSelector.atIndex(fromCurrencyIdx)
        binding.otherCurrencySelector.atIndex(toCurrencyIdx)
    }

    private fun swapIndices() {
        val temp = fromCurrencyIdx
        fromCurrencyIdx = toCurrencyIdx
        toCurrencyIdx = temp
    }

    private fun navigateToHistoricalRatesScreen() {
        val action =
            CurrencyConverterFragmentDirections.actionCurrencyConverterFragmentToHistoricalRatesFragment(
                fromCurrencyCode,
                toCurrencyCode
            )
        findNavController().navigate(action)
    }
}