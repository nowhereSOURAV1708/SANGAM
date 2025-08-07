package com.example.sangam01

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class GroupDetailsActivity : AppCompatActivity() {

    private lateinit var tvGroupName: TextView
    private lateinit var ivQRCode: ImageView
    private lateinit var lvMembers: ListView
    private lateinit var btnAddMember: Button
    private lateinit var btnRemoveMember: Button
    private lateinit var btnDownloadQR: Button
    private lateinit var map: MapView
    private lateinit var btnLost: Button
    private lateinit var lostCard: CardView
    private lateinit var spinnerMembers: Spinner
    private lateinit var btnConfirmLost: Button


    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))
        setContentView(R.layout.activity_group_details)

        tvGroupName = findViewById(R.id.tvGroupName)
        ivQRCode = findViewById(R.id.ivQRCode)
        lvMembers = findViewById(R.id.lvMembers)
        btnDownloadQR = findViewById(R.id.btnDownloadQR)
        btnAddMember = findViewById(R.id.btnAddMember)
        btnRemoveMember = findViewById(R.id.btnRemoveMember)
        map = findViewById(R.id.map)

        map.setMultiTouchControls(true)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            showMapWithMarkers()
        }

        val groupName = intent.getStringExtra("GROUP_NAME") ?: "Unknown Group"
        Log.d("GroupDetailsActivity", "Received GROUP_NAME: $groupName")

        val loggedInAdminEmail = UserSession.getAdminEmail(this)
        if (loggedInAdminEmail.isNullOrEmpty()) {
            Log.e("GroupDetailsActivity", "Error: Admin is not logged in!")
            Toast.makeText(this, "Error: Admin not logged in!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (DataStore.groups.isEmpty()) {
            Log.e("GroupDetailsActivity", "No groups available in DataStore!")
            Toast.makeText(this, "No groups found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val group = DataStore.groups.find { it.admin.email == loggedInAdminEmail }
        if (group == null) {
            Log.e("GroupDetailsActivity", "No group found for admin: $loggedInAdminEmail")
            Toast.makeText(this, "You are not an admin of any group!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        tvGroupName.text = group.groupName
        lvMembers.adapter = MemberAdapter(this, group.members)

        if (group.admin.qrCodeResId != 0) {
            ivQRCode.setImageResource(group.admin.qrCodeResId)
        } else {
            Log.e("GroupDetailsActivity", "QR Code resource ID is invalid!")
            Toast.makeText(this, "QR Code not found for this group!", Toast.LENGTH_SHORT).show()
        }

        btnDownloadQR.setOnClickListener { saveQRCodeToGallery() }

        btnAddMember.setOnClickListener {
            Toast.makeText(this, "In development mode: You can't add members. This feature is coming soon!", Toast.LENGTH_SHORT).show()
        }

        btnRemoveMember.setOnClickListener {
            Toast.makeText(this, "In development mode: You can't remove members. This feature is coming soon!", Toast.LENGTH_SHORT).show()
        }
        btnLost = findViewById(R.id.btnlost)
        lostCard = findViewById(R.id.lostCard)
        spinnerMembers = findViewById(R.id.spinnerMembers)
        btnConfirmLost = findViewById(R.id.btnConfirmLost)

        /*btnLost.setOnClickListener {
            val memberNames = group.members.map { it.fullName }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, memberNames)
            spinnerMembers.adapter = adapter

            lostCard.visibility = View.VISIBLE
        }

        btnConfirmLost.setOnClickListener {
            val selectedMember = spinnerMembers.selectedItem as String
            lostCard.visibility = View.GONE
            Toast.makeText(this, "Alert sent: $selectedMember is reported lost. Authorities have been notified.", Toast.LENGTH_LONG).show()

            // You can also add actual alert logic here: e.g., Firebase, SMS, etc.
        }*/

    }

    private fun saveQRCodeToGallery() {
        val bitmap = (ivQRCode.drawable as? BitmapDrawable)?.bitmap
        if (bitmap == null) {
            Toast.makeText(this, "QR Code not found!", Toast.LENGTH_SHORT).show()
            return
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "QRCode_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/QR Codes")
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                Toast.makeText(this, "QR Code saved to gallery!", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Failed to save QR Code!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showMapWithMarkers() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showMapWithMarkers()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }


}
