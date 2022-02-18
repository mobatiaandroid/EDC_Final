package com.edc.ae.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edc.ae.R
import com.edc.ae.model.ContactUsResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_contact_bottom_sheet.*


class ContactBottomSheet : BottomSheetDialogFragment() {

    var passedData: ContactUsResponse.ContactData? = null
    lateinit var mapGoogle: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_contact_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passedData = arguments?.getParcelable("data")
        ContactTitle.text = passedData?.branch_name
        branchAddress.text = passedData?.branch_addresss
        email.text = passedData?.email
//        phone.text = passedData?.phone!![0]
        var phoneList = ""
        for(i in passedData!!.phone.indices){
            phoneList += passedData!!.phone[i]
            if (passedData!!.phone.size > 1 && i != passedData!!.phone.size - 1) phoneList += "\n"
        }
        phone.text = phoneList

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        supportMapFragment?.getMapAsync { map ->
            val location =
                LatLng(passedData?.latitude?.toDouble()!!, passedData?.longitude?.toDouble()!!)

            val zoomLevel = 15f
            map.addMarker(MarkerOptions().position(location))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
            map.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel))
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isScrollGesturesEnabled = true
            map.uiSettings.isZoomGesturesEnabled = true
        }


    }


}
