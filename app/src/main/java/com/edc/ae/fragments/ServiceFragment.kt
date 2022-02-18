package com.edc.ae.Fragments

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edc.ae.BaseActivities.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.adapter.ServicesAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.ServiceResponseModel
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.fragment_service.contactRec
import kotlinx.android.synthetic.main.fragment_service.navBtn
import kotlinx.coroutines.launch

class ServiceFragment : Fragment() {
    val servicesArray = arrayListOf<ServiceResponseModel.Data>()


    private val requiredPermissionList = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private var download_file_url =
        "https://cms.naismanila.edu.ph/nais_manila/media/images/5c1efca18f836School%20Transportion%20Policy.pdf"
    var per = 0f
    private val PERMISSION_CODE = 4040

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (checkAndRequestPermission())
            launchPdf()
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }        }


//        contactRec.adapter = ServicesAdapter(servicesArray) {
//           //
//
//            //Intent intent=
//        }
        contactRec.adapter = ServicesAdapter(servicesArray, context as Activity)
        callAPI()
    }

    private fun launchPdf() {/*
        startActivity(
            PdfViewerActivity.launchPdfFromUrl(
                activity, download_file_url,
                "EDC", "dir", true
            )
        )*/
    }
    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getServiceData()

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()

                        servicesArray.addAll(call.data)
                        contactRec.adapter?.notifyDataSetChanged()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun checkAndRequestPermission(): Boolean {
        val permissionsNeeded = ArrayList<String>()

        for (permission in requiredPermissionList) {
            if (activity?.let { ContextCompat.checkSelfPermission(it, permission) } !=
                PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(permission)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    permissionsNeeded.toTypedArray(),
                    PERMISSION_CODE
                )
            }
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                val readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readPermission && writePermission)
                    launchPdf()
                else {
                    Toast.makeText(activity, " Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}