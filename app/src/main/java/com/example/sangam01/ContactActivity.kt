package com.example.sangam01

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.preference.PreferenceManager

class ContactActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var memberListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        setContentView(R.layout.activity_contact)

        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            showMapWithMarkers()
        }

        memberListView = findViewById(R.id.memberListView)
        val selectedGroupName = intent.getStringExtra("GROUP_NAME") ?: ""
        val matchedGroup = DataStore.groups.find { it.groupName.equals(selectedGroupName, ignoreCase = true) }

        if (matchedGroup != null) {
            memberListView.adapter = MemberAdapter(this, matchedGroup.members)
        } else {
            Toast.makeText(this, "Group not found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showMapWithMarkers() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentPoint = GeoPoint(location.latitude, location.longitude)
                map.controller.setZoom(15.0)
                map.controller.setCenter(currentPoint)

                val myMarker = Marker(map)
                myMarker.position = currentPoint
                myMarker.title = "Your Location"
                map.overlays.add(myMarker)
            }

            val group = DataStore.groups.find { it.groupName == intent.getStringExtra("GROUP_NAME") }
            group?.members?.forEach { member ->
                val memberLocation = GeoPoint(member.latitude, member.longitude)
                val marker = Marker(map)
                marker.position = memberLocation
                marker.title = member.fullName
                map.overlays.add(marker)
            }

            map.invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}
