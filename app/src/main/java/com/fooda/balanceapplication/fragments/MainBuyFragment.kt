package com.fooda.balanceapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.activities.LoginActivity
import com.fooda.balanceapplication.databinding.FragmentMainBuyBinding
import com.fooda.balanceapplication.databinding.FragmentSettingsBinding
import com.fooda.balanceapplication.databinding.FragmentWalletBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import javax.inject.Inject

class MainBuyFragment : BaseFragment() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    private val args: MainBuyFragmentArgs by navArgs()
    // View Binding
    private var _binding: FragmentMainBuyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBuyBinding.inflate(inflater, container, false)
        _binding?.apply {
            mainZain.setOnClickListener {

                    val action =MainBuyFragmentDirections.actionMainBuyFragmentToZainFragment(args.MainType)
                    findNavController().navigate(action)



            }
            mainMtn.setOnClickListener {
                val action =MainBuyFragmentDirections.actionMainBuyFragmentToMtnFragment(args.MainType)
                findNavController().navigate(action)
            }
            mainSudani.setOnClickListener {
                val action =MainBuyFragmentDirections.actionMainBuyFragmentToSudaniFragment(args.MainType)
                findNavController().navigate(action)
            }
        }
        return binding.root
    }
}
