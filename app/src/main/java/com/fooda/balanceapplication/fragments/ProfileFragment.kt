package com.fooda.balanceapplication.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.FragmentProfileBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.phone.Profile
import com.fooda.balanceapplication.viewmodels.UploadImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputLayout
import com.afollestad.materialdialogs.input.input
import com.bumptech.glide.Glide
import com.fooda.balanceapplication.activities.LoginActivity
import com.fooda.balanceapplication.utilits.UploadRequestBody
import com.fooda.balanceapplication.utilits.getFileName
import com.fooda.balanceapplication.viewmodels.UpdateProfileViewModel
import okhttp3.MultipartBody

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    private val TAG = "ProfileFragment"
    private var selectedImageUri: Uri? = null

    private val uploadImageViewModel: UploadImageViewModel by viewModels()
    private val updateProfileViewModel: UpdateProfileViewModel by viewModels()

    // View Binding
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mHelper: PreferenceHelper
    private var name: TextView? = null
    private var balance: TextView? = null
    private var zain: TextView? = null
    private var mtn: TextView? = null
    private var sudani: TextView? = null
    private var imageView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var edit_zain: TextView? = null
    private var edit_mtn: TextView? = null
    private var edit_sudani: TextView? = null
    private var add_zain_num: ImageView? = null
    private var add_mtn_num: ImageView? = null
    private var add_sudani_num: ImageView? = null



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        _binding?.apply {
            name = userInfoName
            balance = userInfoBalance
            zain = userInfoZain
            mtn = userInfoMtn
            sudani = userInfoSudain
            imageView = userInfoImage
            progressBar = userInfoPro
            edit_zain = editUserZain
            edit_mtn = editUserMtn
            edit_sudani = editUserSudani
            add_zain_num=addZain
            add_mtn_num=addMtn
            add_sudani_num=addSudani

        }
        if (!mHelper.isUserLogged()) {
          hideAddsAndUpdate()
        }
        else{
            showNumberTexts()

        }
        edit_zain?.setOnClickListener() {
            addOrUpdateZain()


        }
        edit_mtn?.setOnClickListener() {
            addOrUpdateMtn()


        }
        edit_sudani?.setOnClickListener() {

            addOrUpdateSudani()

        }
        add_zain_num?.setOnClickListener(){
            addOrUpdateZain()
        }
        add_mtn_num?.setOnClickListener(){
            addOrUpdateMtn()
        }
        add_sudani_num?.setOnClickListener(){
            addOrUpdateSudani()
        }
        setData()
        imageView?.setOnClickListener {
            if (mHelper.isUserLogged()) {
                showUploadDialog()
            } else {
                showErrorDialog(getString(R.string.upload_image_error), getString(R.string.upload_image_error_msg))
            }
        }
        //editUserPhone(mHelper.getPhoneZain()!!, mHelper.getPhoneMtn()!!, "0912000003")
        return binding.root
    }

    private fun addOrUpdateMtn() {
        context?.let { it1 ->
            MaterialDialog(it1).show {
                title(R.string.change_number)
                input(
                        hint = getString(R.string.inter_phone_number),
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    editUserPhone(mHelper.getPhoneZain()!!, text.toString(), mHelper.getPhoneSudani()!!)
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)

            }
        }
    }

    private fun addOrUpdateZain() {
        context?.let { it1 ->
            MaterialDialog(it1).show {
                title(R.string.change_number)
                input(
                        hint = getString(R.string.inter_phone_number),
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    if(validatePhone(text.toString())){
                        editUserPhone(text.toString(), mHelper.getPhoneMtn()!!, mHelper.getPhoneSudani()!!)
                    }
                    else{
                        longErrorToast(getString(R.string.unvalidated_number))
                    }

                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)

            }
        }
    }

    private fun addOrUpdateSudani() {
        context?.let { it1 ->
            MaterialDialog(it1).show {
                title(R.string.change_number)
                input(
                        hint = getString(R.string.inter_phone_number),
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    editUserPhone(mHelper.getPhoneZain()!!, mHelper.getPhoneMtn()!!, text.toString())
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)

            }
        }
    }

    private fun showNumberTexts() {
        zain?.visibility=View.VISIBLE
       mtn?.visibility=View.VISIBLE
        sudani?.visibility=View.VISIBLE
        if (mHelper.getPhoneZain()==""){
            add_zain_num?.visibility=View.VISIBLE
            edit_zain?.visibility=View.GONE

        }
        if (mHelper.getPhoneMtn()==""){
            add_mtn_num?.visibility=View.VISIBLE
            edit_mtn?.visibility=View.GONE

        }
        if (mHelper.getPhoneSudani()==""){
            add_sudani_num?.visibility=View.VISIBLE
            edit_sudani?.visibility=View.GONE
        }

    }

    private fun hideAddsAndUpdate() {
        edit_zain?.visibility=View.GONE
        edit_mtn?.visibility=View.GONE
        edit_sudani?.visibility=View.GONE


    }

    private fun editUserPhone(zain: String, mtn: String, sudani: String) {
        if (mHelper.isUserLogged()) {
            updateProfileViewModel.updateResult(zain, mtn, sudani)
        } else {
            showNoLoginDialog()
        }
    }

    private fun showNoLoginDialog() {
        context?.let {
            MaterialDialog(it).show {
                title(R.string.not_registred_title)
                message(R.string.not_registred_message)
                positiveButton(R.string.agree) {
                    cancel()
                }
                negativeButton(R.string.disagree) {
                    cancel()
                    startActivity(Intent(context, LoginActivity::class.java))
                }

            }
        }
    }

    private fun showUploadDialog() {
        context?.let {
            MaterialDialog(it).show {
                title(R.string.upload_image)
                message(R.string.upload_image_msg)
                positiveButton(R.string.agree) {
                    cancel()
                }
                negativeButton(R.string.disagree) {
                    cancel()
                    openImageChooser()
                }

            }
        }
    }


    private fun setData() {
        val profile = mHelper.getUserID()?.let { Profile(it, mHelper.getUserName(), mHelper.getBalance(), mHelper.getPhoneZain(), mHelper.getPhoneMtn(), mHelper.getPhoneSudani()) }
        name?.text = profile?.name
        balance?.text = profile?.balance.toString()
        zain?.text = profile?.zain
        mtn?.text = profile?.mtn
        sudani?.text = profile?.sudani
        if (mHelper.isUserLogged()) {

            if (mHelper.isImageUploaded()) {
                uploadImageFromUrl()
                // imageView?.setImageURI(Uri.parse(mHelper.getUserImage()))
            } else {
                hideProgressBar()
            }

        } else {
            hideProgressBar()
        }
        updateImageObserveData()
        UpdateNumbersObserveData()
    }

    private fun uploadImageFromUrl() {
        Glide.with(requireContext())
                .load(mHelper.getUserImage())
                .into(imageView!!)
                .also {
                    hideProgressBar()
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    imageView?.setImageURI(selectedImageUri)

                    uploadImage()

                }
            }
        }
    }

    private fun updateImageObserveData() {
        uploadImageViewModel.imageLiveData.observe(this.viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (it.data.error) {
                        showErrorDialog(
                                getString(R.string.app_error),
                                getString(R.string.upload_image_error)
                        )
                        hideProgressBar()
                    } else {
                        mHelper.saveImageUri(it.data.image)
                        mHelper.saveIsImageUploaded()
                        longSuccessToast(it.data.message)
                        uploadImageFromUrl()

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

    private fun UpdateNumbersObserveData() {
        updateProfileViewModel.updateProfileLiveData.observe(this.viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (it.data.error) {
                        showErrorDialog(
                                getString(R.string.app_error),
                                getString(R.string.upload_image_error)
                        )
                        hideProgressBar()
                    } else {

                        mHelper.savePhones(it.data.phone)
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

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            showErrorDialog(getString(R.string.upload_image_error),getString(R.string.upload_image_error_msg))
            return
        }

        val parcelFileDescriptor =
                context?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null)
                        ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(context?.cacheDir, context?.contentResolver!!.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequestBody(file, "image")

        uploadImageViewModel.uploadImage(MultipartBody.Part.createFormData(
                "image",
                file.name,
                body
        ))
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }
}