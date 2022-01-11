package com.edc.ad.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edc.ad.R
import com.edc.ad.model.ContactUsResponse
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_contact_bottom_sheet.*


class ContactBottomSheet : BottomSheetDialogFragment(), OnMapReadyCallback {

    var passedData: ContactUsResponse.ContactData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mapFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.googleMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        return inflater.inflate(R.layout.fragment_contact_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passedData = arguments?.getParcelable("data")

        ContactTitle.text = passedData?.branch_name
        branchAddress.text = passedData?.branch_addresss
        email.text = passedData?.email
        phone.text = passedData?.phone!![0]

    }

    override fun onMapReady(map: GoogleMap) {
        var location =
            LatLng(passedData?.latitude?.toDouble()!!, passedData?.latitude?.toDouble()!!)
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(passedData?.branch_name)
        )
    }

}
