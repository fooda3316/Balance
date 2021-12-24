package com.fooda.balanceapplication.helpers

import android.content.Context
import android.content.SharedPreferences
import com.fooda.balanceapplication.models.registeration.Phone
import com.fooda.balanceapplication.models.registeration.User
import com.fooda.balanceapplication.utilits.AppConstants.IS_FIRST_LUNCH
import com.fooda.balanceapplication.utilits.AppConstants.IS_USER_IMAGE_UPLOADED
import com.fooda.balanceapplication.utilits.AppConstants.IS_USER_LOGGED
import com.fooda.balanceapplication.utilits.AppConstants.SHARED_MODE
import com.fooda.balanceapplication.utilits.AppConstants.SHARED_NAME
import com.fooda.balanceapplication.utilits.AppConstants.USER_BALANCE
import com.fooda.balanceapplication.utilits.AppConstants.USER_ID
import com.fooda.balanceapplication.utilits.AppConstants.USER_IMAGE
import com.fooda.balanceapplication.utilits.AppConstants.USER_NAME
import com.fooda.balanceapplication.utilits.AppConstants.USER_PHONE_MTN
import com.fooda.balanceapplication.utilits.AppConstants.USER_PHONE_SUDANI
import com.fooda.balanceapplication.utilits.AppConstants.USER_PHONE_ZAIN
import com.fooda.balanceapplication.utilits.AppConstants.USER_TOKEN
import com.fooda.balanceapplication.utilits.AppConstants.VALIDATION_CODE
import javax.inject.Inject

class PreferenceHelper @Inject constructor(private val context: Context) {
    private var preferences: SharedPreferences
    init {
        preferences=
            context.getSharedPreferences(SHARED_NAME, SHARED_MODE)
    }



    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun saveIsLoggedIn(user: User, phone: Phone, user_token:String){
        preferences.edit {
            it.putBoolean(IS_USER_LOGGED, true)
            it.putInt(USER_ID, user.id)
            it.putString(USER_NAME, user.name)
            //it.putString(USER_IMAGE, user.image)

            it.putString(USER_TOKEN, user_token)

        }
        savePhones(phone)
    }

     fun savePhones(phone: Phone) {
         preferences.edit {

             it.putString(USER_PHONE_ZAIN, phone.zain)
             it.putString(USER_PHONE_MTN, phone.mtn)
             it.putString(USER_PHONE_SUDANI, phone.sudani)
         }
    }
    fun saveUserBalance(balance: String) {
        preferences.edit {
            it.putString(USER_BALANCE, balance)
        }
    }
    fun saveValidateCode(r: String) {
        preferences.edit {
            it.putString(VALIDATION_CODE, r)
        }
    }
    fun savePhoneNumber(phone: String) {
        preferences.edit {
            it.putString(USER_PHONE_MTN, phone)
        }
    }
    fun isUserLogged():Boolean{
      return  preferences.getBoolean(IS_USER_LOGGED, false)
    }
    fun isFirstLunch():Boolean{
        return  preferences.getBoolean(IS_FIRST_LUNCH, false)
    }

    fun getValidateCode():String?{
        return  preferences.getString(VALIDATION_CODE,"")
    }
    fun getUserID():Int?{
        return  preferences.getInt(USER_ID,0)
    }
    fun getUserPhoneNumber():String?{
        return  preferences.getString(USER_PHONE_MTN,"No number")
    }
    fun getUserName():String?{
        return  preferences.getString(USER_NAME,"no name")
    }
    fun getUserImage():String?{
        return  preferences.getString(USER_IMAGE,"")
    }
    fun getUserToken():String?{
        return  preferences.getString(USER_TOKEN,"")
    }
    fun getBalance():Int?{
        return  preferences.getInt(USER_BALANCE,0)
    }
    fun saveFirstLunch() {
        preferences.edit {
            it.putBoolean(IS_FIRST_LUNCH, true)
        }
    }
    fun saveFirstLunchFalse() {
        preferences.edit {
            it.putBoolean(IS_FIRST_LUNCH, false)
        }
    }

    fun saveRegisterData(phone: String,name:String) {
        preferences.edit {
            it.putString(USER_PHONE_MTN, phone)
            it.putString(USER_NAME, name)

        }
    }
    fun getPhoneZain():String?{
        return  preferences.getString(USER_PHONE_ZAIN,"")
    }
    fun getPhoneMtn():String?{
        return  preferences.getString(USER_PHONE_MTN,"")
    }
    fun getPhoneSudani():String?{
        return  preferences.getString(USER_PHONE_SUDANI,"")
    }

    fun isImageUploaded(): Boolean {
        return  preferences.getBoolean(IS_USER_IMAGE_UPLOADED, false)
    }
    fun saveIsImageUploaded() {
        preferences.edit {
            it.putBoolean(IS_USER_IMAGE_UPLOADED, true)
        }
    }

    fun saveImageUri(image: String) {
        preferences.edit {
            it.putString(USER_IMAGE, image)
        }
    }
}