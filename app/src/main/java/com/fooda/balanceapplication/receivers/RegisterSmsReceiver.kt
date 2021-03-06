package com.fooda.balanceapplication.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.fooda.balanceapplication.activities.FinishLoginActivity
import com.fooda.balanceapplication.activities.FinishRegisterActivity
import com.fooda.balanceapplication.activities.HomeActivity

class RegisterSmsReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        try {
            if (bundle != null) {

                val pdusObj = bundle.get("pdus") as Array<Any>
                for (aPdusObj in pdusObj) {
                    val currentMessage = getIncomingMessage(aPdusObj, bundle)
                    val senderNum = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody
                    Log.d("Sms Receiver", "senderNum: $senderNum; message: $message")


                    val showSmsIntent = Intent(context, FinishRegisterActivity::class.java)
                    showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    showSmsIntent.putExtra(HomeActivity.EXTRA_SMS_NO, senderNum)
                    showSmsIntent.putExtra(HomeActivity.EXTRA_SMS_MESSAGE, message)
                    val start=message[0].toString()
                    if (start.equals("1")){
                        context?.startActivity(showSmsIntent)
                    }
                    else{
                        val showSmsIntent = Intent(context, FinishLoginActivity::class.java)
                        showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        showSmsIntent.putExtra(HomeActivity.EXTRA_SMS_NO, senderNum)
                        showSmsIntent.putExtra(HomeActivity.EXTRA_SMS_MESSAGE, message)
                        context?.startActivity(showSmsIntent)
                    }

                }
            }
        } catch (e: Exception) {
            Log.d("Sms Receiver", "Exception smsReceiver$e")
        }
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val currentSMS: SmsMessage
        if (Build.VERSION.SDK_INT >= 23) {
            val format = bundle.getString("format")
            currentSMS = SmsMessage.createFromPdu(aObject as ByteArray, format)
        } else currentSMS = SmsMessage.createFromPdu(aObject as ByteArray)
        return currentSMS
    }

}