package com.fooda.balanceapplication.activities

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.utilits.CustomProgressBar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import dagger.hilt.android.AndroidEntryPoint
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest

@AndroidEntryPoint
open class BaseActivity: AppCompatActivity() {
    private  val TAG = "BaseActivity"
    private val progressBar: CustomProgressBar = CustomProgressBar()

    fun showErrorDialog(title: String, description: String) {
        AestheticDialog.Builder(this, DialogStyle.RAINBOW, DialogType.ERROR)
            .setTitle(title)
            .setMessage(description)
            .setDuration(2000)
            .show()
    }

    fun showSuccessDialog(title: String, description: String) {
        AestheticDialog.Builder(this, DialogStyle.RAINBOW, DialogType.SUCCESS)
            .setTitle(title)
            .setMessage(description)
            .setDuration(2000)
            .show()
    }
    protected fun showDialog(){
        progressBar.show(this, getString(R.string.please_wait))

    }
     fun dismissDialog() {
        try {
            if (progressBar.dialog.isShowing) {
                progressBar.dialog.dismiss()
            }
        } catch (ex: Exception) {
            ex.message?.let {
                showErrorDialog(
                    getString(R.string.app_error),
                    it
                )
            }
        }
    }
     fun validatePhone(phoneNumber: String): Boolean {
        var result:Boolean=false
        val zeroNum =phoneNumber[0].toString()
        val firstNumber= phoneNumber[1].toString()
        val secondNumber= phoneNumber[2].toString()
        //Log.d(TAG, "validatePhone: phoneNumber[0]  ${phoneNumber[0]} ")
        Log.d(TAG, " is zeroNum number 0:  ${zeroNum.equals(0)} zeroNum is: $zeroNum ")

        if ( zeroNum.isFieldsIsSame("0")) {
            Log.d(TAG, "validatePhone: firstNumber $firstNumber , secondNumber: $secondNumber ")

            if (firstNumber.equals("1")) {
                result = true
            }
            if (firstNumber.equals("9")) {
                result = when (secondNumber.toInt()) {
                    9, 2, 0, 6, 1 -> {
                        true
                    }
                    else -> {
                        false
                    }
                }

            }
        }
        return result
    }
    fun checkRuntimePermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "permission has been granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).withErrorListener {
                Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
            }.check()
    }
     fun longSuccessToast(message: String) {
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        showSuccessDialog("Login Success",message)
        Log.d(TAG, "longToast message: $message")
    }
     fun longErrorToast(message: String) {
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        showErrorDialog("Login Success",message)
        Log.d(TAG, "longToast message: $message")
    }
    fun String.appendData(s1:String,s2:String,s3:String): String {
        return s1+s2+s3+this
    }
    private fun String.isFieldsIsSame(field:String): Boolean {
        return this == field
    }
}