package com.hassanmohammed.currencyapp.ui.fragments.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hassanmohammed.currencyapp.R
import com.hassanmohammed.currencyapp.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp.utils.fragmentViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(R.layout.fragment_currency_converter) {
    private val binding by fragmentViewBinding(FragmentCurrencyConverterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}