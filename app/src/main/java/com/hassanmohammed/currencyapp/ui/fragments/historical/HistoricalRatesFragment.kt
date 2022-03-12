package com.hassanmohammed.currencyapp.ui.fragments.historical

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentHistoricalRatesBinding
import com.hassanmohammed.currencyapp.ui.adapters.HistoricalRateRecyclerAdapter
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
    private val args by navArgs<HistoricalRatesFragmentArgs>()

    @Inject
    lateinit var historicalRateRecyclerAdapter: HistoricalRateRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHistoricalRates()
        setupHistoricalRatesRecycler()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        startCollectOnStarted {
            viewModel.uiState.collect { result ->
                result.data?.let {
                    if (it.isNotEmpty()) {
                        historicalRateRecyclerAdapter.submitList(it)
                        showSnackbar(it.first().errorMessage)
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
    }

    private fun setupHistoricalRatesRecycler() {
        binding.historicalRatesList.run {
            adapter = historicalRateRecyclerAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    VERTICAL
                )
            )
        }

    }

    private fun getHistoricalRates() {
        viewModel.getHistoricalRates(args.fromCurrency, args.toCurrency)

    }

}