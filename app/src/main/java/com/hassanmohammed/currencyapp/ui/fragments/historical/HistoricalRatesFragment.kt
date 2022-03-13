package com.hassanmohammed.currencyapp.ui.fragments.historical

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentHistoricalRatesBinding
import com.hassanmohammed.currencyapp.ui.adapters.CurrenciesConversionRecyclerAdapter
import com.hassanmohammed.currencyapp.ui.adapters.HistoricalRateRecyclerAdapter
import com.hassanmohammed.currencyapp.ui.viewmodel.currency.CurrencyConvertViewModel
import com.hassanmohammed.currencyapp.ui.viewmodel.historical.HistoricalRateViewModel
import com.hassanmohammed.currencyapp.utils.UiEvent
import com.hassanmohammed.currencyapp.utils.fragmentViewBinding
import com.hassanmohammed.currencyapp.utils.showSnackbar
import com.hassanmohammed.currencyapp.utils.startCollectOnStarted
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HistoricalRatesFragment : Fragment(R.layout.fragment_historical_rates) {
    private val binding by fragmentViewBinding(FragmentHistoricalRatesBinding::bind)
    private val viewModel by viewModels<HistoricalRateViewModel>()
    private val currencyConverterViewModel by viewModels<CurrencyConvertViewModel>()
    private val args by navArgs<HistoricalRatesFragmentArgs>()

    @Inject
    lateinit var historicalRateRecyclerAdapter: HistoricalRateRecyclerAdapter

    @Inject
    lateinit var currenciesConversionRecyclerAdapter: CurrenciesConversionRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passValuesToDataBinding()
        getHistoricalRates()
        convertBaseCurrencyInto10PopularCurrencies()
        setupRecyclerViews()
        subscribeObservers()
    }

    private fun passValuesToDataBinding() {
        binding.run {
            fromCurrency = args.fromCurrency
            toCurrency = args.toCurrency
            viewModel = this.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun subscribeObservers() {
        startCollectOnStarted {
            viewModel.uiState.collect { result ->
                result.data?.let {
                    if (it.isNotEmpty()) {
                        historicalRateRecyclerAdapter.submitList(it)
                        showSnackbar(it.first().errorMessage)
                    } else {
                        binding.historicalRatesList.isVisible = false
                    }
                }
            }
        }

        startCollectOnStarted {
            viewModel.uiEvent.collect {
                when (it) {
                    is UiEvent.ShowSnackBar -> showSnackbar(it.message)
                }
            }
        }

        startCollectOnStarted {
            currencyConverterViewModel.uiState.collect {
                it.currenciesRateConverters?.let { rates ->
                    if (rates.isNotEmpty())
                        currenciesConversionRecyclerAdapter.submitList(rates)
                    else
                        binding.currnciesList.isVisible = false
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.historicalRatesList.run {
            adapter = historicalRateRecyclerAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    VERTICAL
                )
            )
        }

        binding.currnciesList.run {
            adapter = currenciesConversionRecyclerAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), VERTICAL)
            )
        }

    }

    private fun getHistoricalRates() {
        viewModel.getHistoricalRates(args.fromCurrency, args.toCurrency)
    }

    private fun convertBaseCurrencyInto10PopularCurrencies() {
        currencyConverterViewModel.convert(
            args.fromCurrency,
            args.amount,
            arrayOf("USD", "EUR", "JPY", "GBP", "CHF", "CAD", "ZAR")
        )
    }

}