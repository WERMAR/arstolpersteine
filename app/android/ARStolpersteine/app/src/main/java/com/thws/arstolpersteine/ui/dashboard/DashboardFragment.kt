package com.thws.arstolpersteine.ui.dashboard

//import androidx.appcompat.app.AppCompatActivity


//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.pm.PackageManager
//import android.location.Location
import android.os.Bundle
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
//import androidx.core.app.ActivityCompat.requestPermissions
//import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
//import com.example.arstolpersteine.R
import com.thws.arstolpersteine.databinding.FragmentDashboardBinding
//import com.example.arstolpersteine.ui.dashboard.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
//import com.example.arstolpersteine.ui.dashboard.PermissionUtils.isPermissionGranted
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
//import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class DashboardFragment : Fragment(),
    OnMapReadyCallback {

    private var _binding: FragmentDashboardBinding? = null
    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //for the current location
    //private var permissionDenied = false
    //private lateinit var map: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mapView?.onDestroy()
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        // Add a marker in thws and move the camera
        val thws = LatLng(49.778736483364845, 9.962629091249857)
        googleMap?.addMarker(
            MarkerOptions()
                .position(thws)
                .title("THWS campus"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(thws))
    }
}