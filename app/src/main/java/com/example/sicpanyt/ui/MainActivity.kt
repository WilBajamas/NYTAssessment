package com.example.sicpanyt.ui

import android.Manifest
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.sicpanyt.R
import com.example.sicpanyt.databinding.ActivityMainBinding
import com.example.sicpanyt.services.LocationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var locationService: LocationService

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.toolbar.setupWithNavController(navController)

        locationService.initFusedLocationProvider(this)
        checkAndRequestLocationPermission()
    }

    override fun onResume() {
        super.onResume()

        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()

        stopLocationUpdates()
    }

    private fun checkAndRequestLocationPermission() {
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    startAccessingLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    startAccessingLocation()
                }
                else -> {
                    // No location access granted.
                    binding.locationText.text = "Location disabled, please turn it on in settings"
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun startAccessingLocation() {
        locationService.startAccessingLocation {
            updateLocationText(location = it)
        }
    }

    private fun startLocationUpdates() {
        locationService.startLocationUpdates {
            updateLocationText(location = it)
        }
    }

    private fun stopLocationUpdates() {
        locationService.stopLocationUpdates()
    }

    private fun updateLocationText(location: Location?) {
        val latLong = "${location?.latitude}, ${location?.longitude}"
        binding.locationText.text = latLong
    }
}