package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp.ui.fragments.historical.HistoricalRateViewModel
import com.hassanmohammed.currencyapp.utils.fragmentViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(R.layout.fragment_currency_converter) {
    private val binding by fragmentViewBinding(FragmentCurrencyConverterBinding::bind)
    private val viewModel by viewModels<HistoricalRateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHistoricalRates("2022-3-2022", "EUR", "USD")
    }
}