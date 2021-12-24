package com.fooda.balanceapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.databinding.FragmentSettingsBinding
import com.fooda.balanceapplication.databinding.FragmentWalletBinding

class SettingsFragment : BaseFragment() {
    // View Binding
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _binding?.apply {}
        return binding.root
    }
}
