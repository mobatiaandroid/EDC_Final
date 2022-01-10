package com.edc.ad.fragments

import android.location.Address
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edc.ad.R
import com.edc.ad.model.ContactUsResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_contact_bottom_sheet.*
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.LatLng

import android.location.Geocoder
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import java.io.IOException
import java.util.*


class ContactBottomSheet : BottomSheetDialogFragment() {

    var passedData: ContactUsResponse.ContactData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passedData = arguments?.getParcelable("data")

        ContactTitle.text = passedData?.branchName
        branchAddress.text = passedData?.branchAddresss
        email.text = passedData?.email
        phone.text = passedData?.phone!![0]

    }

}