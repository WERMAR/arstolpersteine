package com.thws.arstolpersteine.ui.dashboard

//import androidx.appcompat.app.AppCompatActivity


//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.pm.PackageManager
//import android.location.Location
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
//import androidx.core.app.ActivityCompat.requestPermissions
//import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
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
import com.google.android.gms.maps.model.Polygon


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

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //for getting the last known location


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //_binding = FragmentDashboardBinding.inflate(inflater, container, false)
        //val root: View = binding.root
        //mapView = binding.mapView
        //mapView?.onCreate(savedInstanceState)
        //mapView?.getMapAsync(this)
        //return root

        val binding = FragmentDashboardBinding.inflate(inflater, container, false)

        //binding.mapView.onCreate(savedInstanceState) эту и нижнюю строчки можно раскомментить
        //binding.mapView.getMapAsync(this)
        // Проверяем разрешение на доступ к точному местоположению
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Разрешение на доступ к точному местоположению уже предоставлено
            // Вы можете продолжить с инициализацией карты
            binding.mapView.onCreate(savedInstanceState)
            binding.mapView.getMapAsync(this)
        } else {
            // Разрешение не предоставлено, запрашиваем его у пользователя
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        return binding.root

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                when {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        // Разрешение на местоположение было предоставлено
                        // Вы можете продолжить с инициализацией карты
                        binding.mapView.onCreate(null)
                        binding.mapView.getMapAsync(this)
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                        // Пользователь отклонил разрешение, но ему уже показывалось запрос разрешения.
                        // Вы можете предоставить дополнительные объяснения о том, почему нужно разрешение.
                        // Например, показать диалоговое окно с объяснением и повторным запросом.
                        binding.mapView.onCreate(null)
                        binding.mapView.getMapAsync(this)
                    }
                    else -> {
                        // Пользователь отклонил разрешение и не хочет больше видеть запрос разрешения.
                        // Вы можете предоставить альтернативные варианты или принять меры в соответствии с вашей логикой.

                    }
                }
            }
        }
    }


    // Внутри вашего фрагмента или активности добавьте константу для кода запроса разрешения
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
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

        // Add a marker in thws and move the camera
        val me = LatLng(13.778736483364845, 9.962629091249857)
        googleMap?.addMarker(
            MarkerOptions()
                .position(me)
                .title("here i am"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(me))

// Get last known location
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        // Add a marker at the last known location and move the camera
                        val lastKnownLocation = LatLng(it.latitude, it.longitude)
                        googleMap?.addMarker(
                            MarkerOptions()
                                .position(lastKnownLocation)
                                .title("Last Known Location"))
                        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(lastKnownLocation))
                    }
                }
        } else {
            // Handle the case where permission is not granted
            // You might want to request permission or handle it according to your logic
        }

    }
}