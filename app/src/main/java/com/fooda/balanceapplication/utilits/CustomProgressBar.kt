package com.fooda.balanceapplication.utilits

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.databinding.ProgressBarBinding
import com.fooda.balanceapplication.fragments.BaseFragment

class CustomProgressBar {

    lateinit var binding: ProgressBarBinding
    lateinit var dialog: Dialog
        private set

    @JvmOverloads
    fun show(
        context: Context,
        title: CharSequence? = null,
        cancelable: Boolean = true,
        cancelListener: DialogInterface.OnCancelListener? = null
    ): Dialog {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ProgressBarBinding.inflate(inflator)
        val view = binding.root
        if (title != null) {
            binding.title.text = title
        }
        dialog = Dialog(context, R.style.NewDialog)
        dialog.setContentView(view)
        dialog.setCancelable(cancelable)
        dialog.setOnCancelListener(cancelListener)
        dialog.show()
        return dialog
    }
}
