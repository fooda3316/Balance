package com.fooda.balanceapplication.activities;

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.fooda.balanceapplication.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import com.fooda.balanceapplication.R


@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val TAG = "MainActivity"

  //  private var smsReceiver: SmsReceiver? = null

    //private val viewModel: BuyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.settingsFragment)
        )
//        Log.d("Hash" , AppSignatureHashHelper(this).appSignatures.toString())
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }


        binding.apply {
            bottomNavigationView.setupWithNavController(navController)

        }

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}