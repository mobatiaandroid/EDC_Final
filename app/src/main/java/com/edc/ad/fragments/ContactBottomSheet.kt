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
//        val geocoder = Geocoder(context, Locale.getDefault())
//        var addresses: List<Address>? = null
//        try {
//            addresses = geocoder.getFromLocationName(passedData!!.branchAddresss, 1)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        val address: Address = addresses!![0]
//        if (addresses.isNotEmpty()) {
//            val latitude: Double = addresses[0].latitude
//            val longitude: Double = addresses[0].longitude
//            val latLng = LatLng(latitude, longitude)
////            googleMap.addMarker(MarkerOptions().position(latLng))
//            googleMap.getMapAsync { p0 ->
//                p0.addMarker(
//                    MarkerOptions()
//                        .position(latLng)
//                        .title("Marker in Sydney")
//                )
//                p0.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//            }
//        }

    }

}