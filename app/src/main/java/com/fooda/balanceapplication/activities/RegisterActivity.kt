package com.fooda.balanceapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.ActivityRegisterBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.utilits.AppConstants.NAME_NUMBER_KEY
import com.fooda.balanceapplication.utilits.AppConstants.PHONE_NUMBER_KEY
import com.fooda.balanceapplication.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    private  val TAG = "LoginActivity"
    private lateinit var binding: ActivityRegisterBinding
   @Inject
     lateinit var preferenceHelper: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        checkRuntimePermission()
        binding.apply {
            btnRegister.setOnClickListener() {
                val name = inputNameRegister.text.toString()
                val phoneNumber = inputPhoneRegister.text.toString()
                //login(name,phoneNumber)
                if (phoneNumber.length == 10||!inputNameRegister.text.isNullOrEmpty()) {
                   // login(phoneNumber)
                   // Log.d(TAG, "onCreate:is Phone validate :${validatePhone(phoneNumber)} ")
                    if (validatePhone(phoneNumber)) {
                        register(name,phoneNumber)
                    } else {
                        showErrorDialog(
                            getString(R.string.app_error),
                            getString(R.string.validate_phone_error)
                        )
                    }
                }
                else{
                    showErrorDialog(
                        getString(R.string.app_error),
                        getString(R.string.validate_phone_error)
                    )
                }
            }
        }
    }


    private fun register(name:String, phone:String){
     //var r:String= Random(999999).toString()
      //  Log.d(TAG, "login: $r")
        //sendValidateCode(phone,r)
        saveValidateCode("1234")
        goToFinishRegisterActivity(name, phone)

    }

    private fun goToFinishRegisterActivity(name: String,phone: String) {
        preferenceHelper.saveRegisterData(phone,name)
        startActivity(Intent(applicationContext,FinishRegisterActivity::class.java))
    }

    private fun saveValidateCode(r: String) {
        Log.d(TAG, "saveValidateCode: ")
        preferenceHelper.saveValidateCode(r)
    }

    private fun sendValidateCode(phone: String, r: String) {
        Log.d(TAG, "sendValidateCode: $r")
    }


}