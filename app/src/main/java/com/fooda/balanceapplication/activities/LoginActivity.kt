package com.fooda.balanceapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.ActivityLoginBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.utilits.AppConstants.PHONE_NUMBER_KEY
import com.fooda.balanceapplication.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private  val TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var isUserFound:Boolean=false
   @Inject
     lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        checkRuntimePermission()
        binding.apply {
            btnLogin.setOnClickListener() {

                val phoneNumber = inputPhoneLogin.text.toString()
                Log.d(TAG, "phoneNumber.length : ${phoneNumber.length} ")
                if (phoneNumber.length == 10) {

                    if (validatePhone(phoneNumber)) {
                        loginViewModel.loginResult(phoneNumber)
                       //  login(phoneNumber)
                    } else {
                        showErrorDialog(
                            getString(R.string.app_error),
                            getString(R.string.validate_phone_error)
                        )
                    }
            }

                else {
                    showErrorDialog(
                        getString(R.string.app_error),
                        getString(R.string.phone_less_error)
                    )
                }
            }
            btnNewAccaunt.setOnClickListener {
                startActivity(Intent(applicationContext,RegisterActivity::class.java))
            }
            btnSkip.setOnClickListener {
                startActivity(Intent(applicationContext,HomeActivity::class.java))
            }
        }
        observeData()

    }


    private fun login(phone:String){
     //var r:String= Random(999999).toString()
      //  Log.d(TAG, "login: $r")
        //sendValidateCode(phone,r)
        saveValidateCode("1234")
        goToFinishLoginActivity(phone)

    }

    private fun goToFinishLoginActivity(phone: String) {
        Log.d(TAG, "goToFinishLoginActivity: ")
        startActivity(Intent(applicationContext,FinishLoginActivity::class.java))
        preferenceHelper.savePhoneNumber(phone)
    }

    private fun saveValidateCode(r: String) {
        Log.d(TAG, "saveValidateCode: $r")
        preferenceHelper.saveValidateCode(r)
    }

    private fun sendValidateCode(phone: String, r: String) {
        Log.d(TAG, "sendValidateCode: $r")
    }

    private fun observeData() {
        loginViewModel.loginLiveData.observe(this, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (it.data.error) {
                        showErrorDialog(
                            getString(R.string.app_error),
                            getString(R.string.phone_not_found)
                        )
                       // isUserFound=true
                    }else{
                        login(it.data.user.phone)
                    }
                }
                is ResultState.Error -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.TimeOut -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.NoInternetConnection -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
            }
        })
    }

}