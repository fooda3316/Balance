package com.fooda.balanceapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.ActivityFinishLoginBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.utilits.AppConstants
import com.fooda.balanceapplication.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinishLoginActivity : BaseActivity() {
    private val TAG = "FinishLoginActivity"
    private val loginViewModel: LoginViewModel by viewModels()
    private var phone: String? = null
    private var message: String? = null
    lateinit var binding: ActivityFinishLoginBinding

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    private var edt1: EditText? = null
    private var edt2: EditText? = null
    private var edt3: EditText? = null
    private var edt4: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getDataFromActivity()
        observeData()
        getMessage(edt1, edt2, edt3, edt4)
        //phone?.let { loginViewModel.doLogin(it) }
        binding.apply {
            //val code=edtTextCode.text.toString()
            if (message == null) {
                edtFour.addTextChangedListener { editable ->
                    editable?.let {
                        //val stringBuilder:StringBuilder()
                        if (edtOne.text.isNotEmpty() || edtTwo.text.isNotEmpty() || edtThree.text.isNotEmpty() || edtFour.text.isNotEmpty()) {
                            if (isCodeCorrect(editable.toString().appendData(edtOne.text.toString(), edtTwo.text.toString(), edtThree.text.toString()))) {
                                phone?.let { it1 -> startActualLogin(it1) }
                            } else {
                                showErrorDialog(
                                        "Login error",
                                        "عفواً الكود الذي ادخلتة غير صحيح الؤجاء ادخال الرقم الصحيح"
                                )
                            }

                        } else {
                            showErrorDialog(
                                    "Login error",
                                    "عفواً الكود الذي ادخلتة غير صحيح الؤجاء ادخال الرقم الصحيح"
                            )
                        }
                    }
                }
            }
            edt1 = edtOne
            edt2 = edtTwo
            edt3 = edtThree
            edt4 = edtFour

        }

    }

    private fun getDataFromActivity() {
        phone = preferenceHelper.getUserPhoneNumber()
    }

    private fun startActualLogin(phone: String) {
        //val loginResult=null
        Log.d(TAG, "startActualLogin: ")

        phone?.let {
            loginViewModel.loginResult(it)

        }
    }

    private fun isCodeCorrect(code: String): Boolean {
        val isCorrect = preferenceHelper.getValidateCode().equals(code)
        Log.d(TAG, "Entered code :$code  Code: ${preferenceHelper.getValidateCode()} isCorrect: $isCorrect")
        if (isCorrect) {
            return true
        }
        return false
    }

    private fun getMessage(edt1: EditText?, edt2: EditText?, edt3: EditText?, edt4: EditText?) {
        val senderNo = intent.getStringExtra(HomeActivity.EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(HomeActivity.EXTRA_SMS_MESSAGE)
        message = senderMessage
        Log.e(
                "Message", """
            Sender  :  $senderNo
            Message : $senderMessage 
        """.trimIndent()
        )
        // 6505551212
        //if (!(senderNo == "d\'bigbox.id")) {
        if (senderNo == "6505551212") {
            val otp = senderMessage?.substring(17, 21)!!.trim()
            //  Log.e("Otp1", otp)

            if (otp != null) {
                Log.e("Otp", otp)
            }
            edt1?.setText(otp[0].toString())
            edt2?.setText(otp[1].toString())
            edt3?.setText(otp[2].toString())
            edt4?.setText(otp[3].toString())
            if (isCodeCorrect(otp)) {
                Log.e("isCodeCorrect", " Correct")
                phone?.let { startActualLogin(it) }
            } else {
                Log.e("isCodeCorrect", "not Correct")
            }

        }

    }

    private fun observeData() {
        loginViewModel.loginLiveData.observe(this, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (!it.data.error) {
                        preferenceHelper.saveIsLoggedIn(it.data.user, it.data.phone, it.data.token)
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        longSuccessToast(it.data.message)
                    } else {
                        showErrorDialog("Login error", "عفواً رقم الهاتف الذي ادخلتة غير صحيح الؤجاء ادخال الرقم الصحيح")

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