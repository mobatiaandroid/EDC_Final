package com.edc.ae.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.activity.HomeBaseUserActivity
import com.edc.ae.activity.SurveyDetailActivity
import com.edc.ae.adapter.SettingsListAdapter
import com.edc.ae.adapter.SurveyListAdapter
import com.edc.ae.api.RetrofitClient
import com.google.android.gms.common.util.Strings
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.edcsurvey.survey.model.SurveyDataModel
import kotlinx.android.synthetic.main.fragment_survey.*
import kotlinx.coroutines.launch
import android.text.method.PasswordTransformationMethod
import com.edc.ae.util.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_alert.*
import org.json.JSONObject
import retrofit2.HttpException


class SettingsFragment : Fragment() {
    lateinit var settingsRecycler: RecyclerView
    lateinit var mContext: Context
    lateinit var settingsArrayList : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        settingsArrayList= ArrayList()
        settingsArrayList.add("Change App Settings")
        settingsArrayList.add("Notification")
        settingsArrayList.add("Change Password")
        initUI()
        navBtn.setOnClickListener { _ ->

            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }


    }

    private fun initUI()
    {
        settingsRecycler = requireView().findViewById(R.id.settingsRecycler) as RecyclerView
        var settingsAdapter= SettingsListAdapter(settingsArrayList,mContext)
        settingsRecycler.adapter=settingsAdapter
        settingsRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                if(position==0)
                {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", mContext.packageName, null)
                    intent.data = uri
                    mContext.startActivity(intent)
                }
                else if (position==2)
                {

                    val dialog = BottomSheetDialog(mContext)
                    val view = layoutInflater.inflate(R.layout.bottom_sheet_change_password, null)
                    val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
                    val editOldPassword = view.findViewById<EditText>(R.id.editOldPassword)
                    val oldPasswordHideShowImg = view.findViewById<ImageView>(R.id.oldPasswordHideShowImg)
                    val editNewPassword = view.findViewById<EditText>(R.id.editNewPassword)
                    val newPasswordHideShowImg = view.findViewById<ImageView>(R.id.newPasswordHideShowImg)
                    val editConfirmPassword = view.findViewById<EditText>(R.id.editConfirmPassword)
                    val confirmPasswordHideShowImg = view.findViewById<ImageView>(R.id.confirmPasswordHideShowImg)
                    var isOldPasswordHide:Boolean=true
                    var isNewPasswordHide:Boolean=true
                    var isConfirmPasswordHide:Boolean=true
                    buttonSubmit.alpha = 0.5f
                    buttonSubmit.isEnabled = false
                    buttonSubmit.isClickable = false
                    oldPasswordHideShowImg.setOnClickListener(View.OnClickListener {
                        if (isOldPasswordHide)
                        {
                            isOldPasswordHide=false
                            editOldPassword.setTransformationMethod(PasswordTransformationMethod())

                        }
                        else{
                            isOldPasswordHide=true
                            editOldPassword.setTransformationMethod(HideReturnsTransformationMethod())
                        }
                    })

                    newPasswordHideShowImg.setOnClickListener(View.OnClickListener {
                        if (isNewPasswordHide)
                        {
                            isNewPasswordHide=false
                            editOldPassword.setTransformationMethod(PasswordTransformationMethod())

                        }
                        else{
                            isNewPasswordHide=true
                            editOldPassword.setTransformationMethod(HideReturnsTransformationMethod())
                        }
                    })
                    confirmPasswordHideShowImg.setOnClickListener(View.OnClickListener {
                        if (isConfirmPasswordHide)
                        {
                            isConfirmPasswordHide=false
                            editOldPassword.setTransformationMethod(PasswordTransformationMethod())

                        }
                        else{
                            isConfirmPasswordHide=true
                            editOldPassword.setTransformationMethod(HideReturnsTransformationMethod())
                        }
                    })

                    editOldPassword.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            if (s!!.isNotEmpty() && editNewPassword.text.toString().trim().isNotEmpty() && editConfirmPassword.text.toString().trim().isNotEmpty()){

                                buttonSubmit.alpha = 1.0f
                                buttonSubmit.isEnabled = true
                                buttonSubmit.isClickable = true

                            }
                            else{
                                buttonSubmit.alpha = 0.5f
                                buttonSubmit.isEnabled = false
                                buttonSubmit.isClickable = false
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }

                    })

                    editNewPassword.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            if (s!!.isNotEmpty() && editOldPassword.text.toString().trim().isNotEmpty() && editConfirmPassword.text.toString().trim().isNotEmpty()){

                                buttonSubmit.alpha = 1.0f
                                buttonSubmit.isEnabled = true
                                buttonSubmit.isClickable = true

                            }
                            else{
                                buttonSubmit.alpha = 0.5f
                                buttonSubmit.isEnabled = false
                                buttonSubmit.isClickable = false
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }

                    })

                    editConfirmPassword.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            if (s!!.isNotEmpty() && editOldPassword.text.toString().trim().isNotEmpty() && editNewPassword.text.toString().trim().isNotEmpty()){

                                buttonSubmit.alpha = 1.0f
                                buttonSubmit.isEnabled = true
                                buttonSubmit.isClickable = true

                            }
                            else{
                                buttonSubmit.alpha = 0.5f
                                buttonSubmit.isEnabled = false
                                buttonSubmit.isClickable = false
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }

                    })

                    buttonSubmit.setOnClickListener(View.OnClickListener {

                        if (editNewPassword.text.toString().trim().equals(editConfirmPassword.text.toString().trim()))
                        {
                            callChangePasswordApi(editOldPassword.text.toString().trim(),editNewPassword.text.toString().trim(),editConfirmPassword.text.toString().trim(),dialog)
                        }
                        else{
                            //Toast

                        }
                    })


                    dialog.setCancelable(true)
                    dialog.setContentView(view)
                    dialog.show()



                }

            }

        })



    }


   private fun callChangePasswordApi(oldPassword:String,newPassword:String,confirmPassword:String,dialog: BottomSheetDialog) {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = this.let { ProgressBarDialog(requireActivity()) }
        progressBarDialog.show()

        lifecycleScope.launch {
            try {

                val paramObject = JsonObject().apply {
                    addProperty("password", oldPassword)
                    addProperty("new_password",newPassword)
                    addProperty("confirm_password",confirmPassword) }
                //  paramObject.put("email", edtEmail.text.toString())
                //    paramObject.put("password", edtPassword.text.toString())
                val call = RetrofitClient.get.changePassword("Bearer "+ PreferenceManager.getAccessToken(mContext as Activity),paramObject)

                when (call.status) {
                    200 -> {
                        progressBarDialog.dismiss()
                        dialog.dismiss()
                    }
                }

            } catch (httpException: HttpException) {
                progressBarDialog.dismiss()

                val responseErrorBody = httpException.response()!!.errorBody()
                val response = responseErrorBody!!.string()
                val obj = JSONObject(response)
                var status_code=obj.getString("status")
                var message = obj.getString("message")
                CommonMethods.showLoginErrorPopUp(
                    mContext,
                    "Alert",
                    message
                )

            }
        }
    }

}