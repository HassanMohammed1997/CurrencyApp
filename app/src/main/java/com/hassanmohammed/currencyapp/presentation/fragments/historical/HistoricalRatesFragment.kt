package com.hassanmohammed.currencyapp.presentation.fragments.historical

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
import com.hassanmohammed.currencyapp.presentation.adapters.CurrenciesConversionRecyclerAdapter
import com.hassanmohammed.currencyapp.presentation.adapters.HistoricalRateRecyclerAdapter
import com.hassanmohammed.currencyapp.presentation.viewmodel.currency.CurrencyConvertViewModel
import com.hassanmohammed.currencyapp.presentation.viewmodel.historical.HistoricalRateViewModel
import com.hassanmohammed.currencyapp.utils.*
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
        collectFlowSafely {
            viewModel.uiState.collect { result ->
                result.data?.let {
                    binding.historicalRatesList.isVisible = it.isNotEmpty()
                    if (it.isNotEmpty()) {
                        historicalRateRecyclerAdapter.submitList(it)
                        binding.historicalErrorMsg.text = it.first().errorMessage
                    }
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

        collectFlowSafely {
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
        if (isNetworkAvailable) {
            showSnackbar(R.string.no_internet_connection)
            return
        }
        currencyConverterViewModel.convert(
            args.fromCurrency,
            args.amount,
            arrayOf("USD", "EUR", "JPY", "GBP", "CHF", "CAD", "ZAR", "SVC", "BOB", "HUF")
        )
    }

}