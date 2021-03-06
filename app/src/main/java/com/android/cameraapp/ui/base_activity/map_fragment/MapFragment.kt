package com.android.cameraapp.ui.base_activity.map_fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.cameraapp.R
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.databinding.MapsFragmentBinding
import com.android.cameraapp.ui.base_activity.BaseActivity
import com.android.nbaapp.data.vms.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment
import java.util.concurrent.Executors
import javax.inject.Inject

const val permissionRequestCode = 205

class MapFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MapFragmentViewModel
    lateinit var binding: MapsFragmentBinding
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var map: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MapFragmentViewModel::class.java)
        binding = MapsFragmentBinding.inflate(inflater, container, false)


        if (checkPremissions()) generateMaps() else ActivityCompat.requestPermissions(
            activity!!,
            permissions,
            permissionRequestCode
        )
        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            if (::map.isInitialized && it != null) {
                val executor = Executors.newSingleThreadExecutor()
                executor.submit {
                    for (i in it) {
                        generateMarkers(map, i)

                    }
                }


            }
        })
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
                    map = it
                    getDeviceLocation(it)
                }
            })
    }

    fun generateMarkers(map: GoogleMap, item: UserCollection.Photos) {
        if (item.longitude != null && item.altitude != null) {
            map.addMarker(
                MarkerOptions().position(LatLng(item.altitude!!, item.longitude!!))
                    .title(item.title)
            )
        }
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

    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).apply {
            BottomBarToInvisible()
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