package com.fooda.balanceapplication.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.ActivityFinishRegisterBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.utilits.AppConstants
import com.fooda.balanceapplication.utilits.UploadRequestBody
import com.fooda.balanceapplication.utilits.getFileName
import com.fooda.balanceapplication.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject
@AndroidEntryPoint
class FinishRegisterActivity : BaseActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private  var  name:String?=null
    private  var  phone:String?=null
    private var edt1:EditText?=null
    private var edt2:EditText?=null
    private var edt3:EditText?=null
    private var edt4:EditText?=null
    private  var  message:String?=null


    lateinit var binding: ActivityFinishRegisterBinding
    @Inject
     lateinit var preferenceHelper: PreferenceHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getDataFromIntent()
        getMessage(edt1,edt2,edt3,edt4)
        binding.apply {
            if (message==null) {
                edtFour.addTextChangedListener { editable ->
                    editable?.let {
                        //val stringBuilder:StringBuilder()
                        if (edtOne.text.isNotEmpty() || edtTwo.text.isNotEmpty() || edtThree.text.isNotEmpty() || edtFour.text.isNotEmpty()) {
                            if (isCodeCorrect(editable.toString().appendData(edtOne.text.toString(), edtTwo.text.toString(), edtThree.text.toString()))) {
                                phone?.let { it1 -> name?.let { it2 -> startActualRegister(it1, it2) } }
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
            edt1=edtOne
            edt2=edtTwo
            edt3=edtThree
            edt4=edtFour


        }

        observeData()
    }

    private fun getDataFromIntent() {
        phone =preferenceHelper.getUserPhoneNumber()
        name =preferenceHelper.getUserName()


    }

    private fun startActualRegister(phone: String,name:String) {
        val type:String=getCompanyType(phone)
        register(name,phone,phone,phone,type)
    }

    private fun getCompanyType(phoneNumber: String): String {
        var type:String="zain"
        val firstNumber= phoneNumber[1].toString()
        val secondNumber= phoneNumber[2].toString()
        //val thirdNumber= phoneNumber[3].toString()
        if (firstNumber.equals("1")){
            type="sudani"
        }
        else{
            when(secondNumber){
                "6","0","1"->{
                    type="zain"
                }
                "9","2"->{
                    type="mtn"
                }
            }
        }


        return type
    }

    private fun isCodeCorrect(code: String): Boolean {
if (preferenceHelper.getValidateCode().equals(code)){
    return true
}
        return false
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun getMessage(edt1: EditText?, edt2: EditText?, edt3: EditText?, edt4: EditText?) {
        val senderNo = intent.getStringExtra(HomeActivity.EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(HomeActivity.EXTRA_SMS_MESSAGE)
        message=senderMessage

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
            if (isCodeCorrect(otp)){
                Log.e("isCodeCorrect", " Correct")
                phone?.let { name?.let { it1 -> startActualRegister(it, it1) } }
            }
            else{
                Log.e("isCodeCorrect", "not Correct")
            }

        }

    }

    private fun observeData() {
        registerViewModel.registerLiveData.observe(this, Observer {
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
                        preferenceHelper.saveIsLoggedIn(it.data.user,it.data.phone, it.data.token)
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        longSuccessToast(it.data.message)
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

    private fun register(name:String,phone:String,password:String,passwordConfirmation:String,type:String) {
        registerViewModel.register(name , phone, password ,passwordConfirmation  , type)

    }

}