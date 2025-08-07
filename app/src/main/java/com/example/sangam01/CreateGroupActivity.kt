package com.example.sangam01

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.UUID
import com.google.zxing.BarcodeFormat




class CreateGroupActivity : AppCompatActivity() {

    private lateinit var etGroupName: EditText
    private lateinit var btnCreateGroup: Button
    private lateinit var ivQRCode: ImageView
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        etGroupName = findViewById(R.id.etGroupName)
        btnCreateGroup = findViewById(R.id.btnCreateGroup)


        btnCreateGroup.setOnClickListener {
            createGroup()
        }

        val btnSkip = findViewById<Button>(R.id.btnSkip)
        btnSkip.setOnClickListener {
            startActivity(Intent(this, GroupDetailsActivity::class.java))
        }
    }


    private fun createGroup() {
        val groupName = etGroupName.text.toString().trim()
        if (groupName.isEmpty()) {
            Toast.makeText(this, "Enter Group Name", Toast.LENGTH_SHORT).show()
            return
        }

        val groupId = UUID.randomUUID().toString() // Unique Group ID
        val userId = auth.currentUser?.uid ?: return

        val groupData = hashMapOf(
            "groupId" to groupId,
            "groupName" to groupName,
            "adminId" to userId,
            "members" to listOf(userId) // Admin is the first member
        )

        firestore.collection("groups").document(groupId)
            .set(groupData)
            .addOnSuccessListener {
                Toast.makeText(this, "Group Created in firebase , feature coming soon ", Toast.LENGTH_SHORT).show()
                generateQRCode(groupId)
                // Navigate to GroupDetailsActivity
                //val intent = Intent(this, GroupDetailsActivity::class.java)
                //intent.putExtra("GROUP_ID", groupId) // Pass Group ID
                //startActivity(intent)
                finish() // Close CreateGroupActivity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create group", Toast.LENGTH_SHORT).show()
            }
    }

    private fun generateQRCode(groupId: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                groupId, BarcodeFormat.QR_CODE, 400, 400 // ✅ FIXED
            )
            ivQRCode.setImageBitmap(bitmap)
            Toast.makeText(this, "qr is created", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
