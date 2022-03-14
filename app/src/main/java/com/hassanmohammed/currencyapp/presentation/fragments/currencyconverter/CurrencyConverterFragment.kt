package com.hassanmohammed.currencyapp.presentation.fragments.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp.presentation.viewmodel.currency.CurrencyConvertViewModel
import com.hassanmohammed.currencyapp.utils.*
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.atIndex
import com.hassanmohammed.currencyapp.utils.BindingAdapterUtil.setItems
import dagger.hilt.android.AndroidEntryPoint

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
        passValuesToDataBinding()
        setCountriesInSpinners()
        setListenerForViews()
        subscribeObserver()

    }

    private fun passValuesToDataBinding() {
        binding.run {
            currencyViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun subscribeObserver() {
        collectFlowSafely {
            viewModel.uiState.collect {
                it.data?.let { currencyConverter ->
                    if (currencyConverter.errorMessage.isNotEmpty())
                        showSnackbar(currencyConverter.errorMessage)
                }
            }
        }

        collectFlowSafely {
            viewModel.uiEvent.collect {
                when (it) {
                    is UiEvent.ShowSnackBar -> showSnackbar(it.message)
                }
            }
        }
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
            convertCurrency()
        }

        binding.detailsBtn.setOnClickListener { navigateToHistoricalRatesScreen() }
    }

    private fun convertCurrency() {
        if (isNetworkAvailable) {
            showSnackbar(R.string.no_internet_connection)
            return
        }
        viewModel.convert(fromCurrencyCode, toCurrencyCode, amount)
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
                toCurrencyCode,
                amount
            )
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        clearSpinnersValues()
    }

    private fun clearSpinnersValues() {
        binding.otherCurrencySelector.setText("", false)
        binding.baseSelector.setText("", false)
    }
}