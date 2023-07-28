package com.atitus.possoajudar

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.atitus.possoajudar.databinding.FragmentFirstBinding
import com.atitus.possoajudar.model.History
import com.atitus.possoajudar.services.ApiState
import com.atitus.possoajudar.viewModels.HistoriesViewModel
import com.atitus.possoajudar.viewModels.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.List as List

@AndroidEntryPoint
class FirstFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var _googleMap: GoogleMap? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val historiesViewModel: HistoriesViewModel by viewModels()
    private var locationViewModel: LocationViewModel? = null
    private var _histories: List<History>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        historiesViewModel.response.observe(this) {
            if (it is ApiState.Success<*>){
                if (_googleMap != null){
                    val histories = it.data as List<History>
                    putMarkers(histories)
                } else {
                    _histories = it.data as List<History>
                }
            }
        }
        historiesViewModel.fetchHistories()
    }

    private fun putMarkers(histories: List<History>){
        histories.forEach { history ->
            val marker = LatLng(history.latitude, history.longitude)
            _googleMap?.addMarker(
                MarkerOptions()
                    .position(marker)
                    .title(history.title)
                    .snippet(history.description)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _googleMap = googleMap
        _histories?.let { putMarkers(it) }
        getLastLocation()
    }

    private fun getLastLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        locationViewModel = LocationViewModel(fusedLocationClient)
        locationViewModel?.response?.observe(this) {
            if (it is ApiState.Success<*>){
                val location: Location = it.data as Location
                location.let { safeLocation ->
                    _googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        LatLng(safeLocation.latitude,safeLocation.longitude), 16.0f)
                    )
                }
            }
        }
        locationViewModel?.fetchCurrentLocation()
    }
}