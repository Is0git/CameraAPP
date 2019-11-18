package com.android.cameraapp.ui.base_activity.map_fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.cameraapp.R
import com.android.cameraapp.databinding.MapsFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val permissionRequestCode = 205

class MapFragment : Fragment() {
    lateinit var binding: MapsFragmentBinding
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MapsFragmentBinding.inflate(inflater, container, false)


        if (checkPremissions()) generateMaps() else ActivityCompat.requestPermissions(
            activity!!,
            permissions,
            permissionRequestCode
        )

        return binding.root
    }

    fun checkPremissions(): Boolean = ContextCompat.checkSelfPermission(
        activity!!.applicationContext,
        permissions[0]
    ) == PackageManager.PERMISSION_GRANTED

    fun generateMaps() {
        val fragment = childFragmentManager.findFragmentById(R.id.map)
        (fragment as SupportMapFragment).getMapAsync(
            OnMapReadyCallback {
                if (it != null) {
                    it.apply {
                        isMyLocationEnabled = true
                        uiSettings.isMyLocationButtonEnabled = true
                    }
//                    val sydney = LatLng(-33.852, 151.211)
//                    it.addMarker(
//                        MarkerOptions().position(sydney)
//                            .title("Marker in Sydney")
////                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.background))
//                    )

                    getDeviceLocation(it)
                }
            })
    }

    fun getDeviceLocation(map: GoogleMap) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        val task = mFusedLocationProviderClient.lastLocation
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                val location = it.result
                map.moveCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            location?.altitude!!,
                            location.longitude
                        )
                    )
                )
            } else {

                map.uiSettings.isMyLocationButtonEnabled = false;
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequestCode && checkPremissions()) generateMaps()
    }

}