package com.fooda.balanceapplication.fragments

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.utilits.CustomProgressBar
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType

open class BaseFragment : Fragment() {
    private  val TAG = "BaseFragment"
    private val progressBar: CustomProgressBar = CustomProgressBar()

    fun showErrorDialog(title: String, description: String) {
        AestheticDialog.Builder(requireActivity(), DialogStyle.RAINBOW, DialogType.ERROR)
            .setTitle(title)
            .setMessage(description)
            .setDuration(2000)
            .show()
    }

    fun showSuccessDialog(title: String, description: String) {
        AestheticDialog.Builder(requireActivity(), DialogStyle.RAINBOW, DialogType.SUCCESS)
            .setTitle(title)
            .setMessage(description)
            .setDuration(2000)
            .show()
    }
    fun showDialog(){
        progressBar.show(requireActivity(), getString(R.string.please_wait))

    }
    fun dismissDialog() {
        try {
            if (progressBar.dialog.isShowing) {
                progressBar.dialog.dismiss()
            }
        } catch (ex: Exception) {
            ex.message?.let {
                displayErrorLog(it)
                showErrorDialog(
                    getString(R.string.dissmiss_error),
                    it
                )
            }
        }
    }

    private fun displayErrorLog(message: String) {
        Log.e(TAG, "displayErrorLog: $message" )

    }
    private fun displayErrors(message: String) {
        showErrorDialog("Login Success",message)
    }
    /*fun openSharingIntent(article: Article) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            article.title + " - via Infotify News App\n" + article.url
        )
        sendIntent.type = "text/plain"
        requireActivity().startActivity(sendIntent)
    }

    fun openDetailsActivity(article: Article) {

        val i = Intent(context, NewsDetailsActivity::class.java)

        // ADD DATA TO OUR INTENT
        i.putExtra("title", article.title)
        i.putExtra("description", article.description)
        i.putExtra("imageUrl", article.urlToImage)
        i.putExtra("source", article.source.name)
        i.putExtra("date", article.publishedAt)
        i.putExtra("content", article.content)
        i.putExtra("url", article.url)

        // START DETAIL ACTIVITY
        requireActivity().startActivity(i)
    }

    fun loadWebviewDialog(article: Article) {
        // show article content inside a dialog
        val newsView = WebView(requireActivity())
        var failedLoading = false
        newsView.settings.loadWithOverviewMode = true

        val title: String = article.title.toString()
        val url: String = article.url

        newsView.settings.javaScriptEnabled = false
        newsView.isHorizontalScrollBarEnabled = false
        newsView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                failedLoading = true
                dismissDialog()
                showErrorDialog(
                    getString(R.string.web_error),
                    getString(R.string.link_not_reachable)
                )
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (!failedLoading) {
                    dismissDialog()
                    val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
                    alertDialog.setTitle(title)
                    alertDialog.setView(newsView)
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, "OK"
                    ) { _, _ -> dismissDialog() }
                    alertDialog.show()
                } else {
                    dismissDialog()
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.show(requireContext(), getString(R.string.please_wait))
            }
        }
        newsView.loadUrl(url)
        newsView.isClickable = false
        newsView.isEnabled = false
    }

    private fun dismissDialog() {
        try {
            if (progressBar.dialog.isShowing) {
                progressBar.dialog.dismiss()
            }
        } catch (ex: Exception) {
            showErrorDialog(
                getString(R.string.web_error),
                getString(R.string.link_not_reachable)
            )
        }
    }*/
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
    fun makeCall(number:String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + number)
        startActivity(dialIntent)
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
    private fun String.isFieldsIsSame(field:String): Boolean {
        return this == field
    }

}
