package com.hassanmohammed.currencyapp.ui.fragments.historical

import androidx.fragment.app.Fragment
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentHistoricalRatesBinding
import com.hassanmohammed.currencyapp.utils.fragmentViewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoricalRatesFragment : Fragment(R.layout.fragment_historical_rates) {
    private val binding by fragmentViewBinding(FragmentHistoricalRatesBinding::bind)

}