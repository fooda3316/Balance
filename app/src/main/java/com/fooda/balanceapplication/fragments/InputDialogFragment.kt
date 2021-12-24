package com.fooda.balanceapplication.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.databinding.FragmentInputDialogBinding
import com.fooda.balanceapplication.input.InputCallback
import com.fooda.balanceapplication.input.SendItem

class InputDialogFragment : DialogFragment() {

    private var callback: InputCallback? = null

    // View Binding
    private var _binding: FragmentInputDialogBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance(callback: InputCallback): InputDialogFragment {
            val fragment = InputDialogFragment()
            val args = Bundle()
          //  args.putSerializable("sendItem", sendItem)
            fragment.callback = callback
            //fragment.arguments = args
            fragment.isCancelable = false
            fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputDialogBinding.inflate(inflater, container, false)

        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            choiceZain.setOnClickListener{
                secretNumberInput.visibility=View.VISIBLE
            }
            btnSendBalance.setOnClickListener {
                var type=""
                if (choiceZain.isChecked){
                    type="zain"
                }
                else if (choiceMtn.isChecked){
                    type="mtn"
                }
                else if (choiceSudani.isChecked){
                type="sudani"
            }
                else {
                    type="zain"
                }
                val number=phoneNumberInput.text.toString()
                val amount=amountInput.text.toString()
                val secret=secretNumberInput.text.toString()
                callback?.onInputFinished(SendItem(type,number,amount,secret))
                dismiss()
            }
        }
    }

}
