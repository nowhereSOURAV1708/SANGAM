package com.example.sangam01

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSaveDetails: Button

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etAge: EditText
    private lateinit var etAddress: EditText
    private lateinit var etFatherName: EditText
    private lateinit var etMotherName: EditText
    private lateinit var etSpouseName: EditText
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var rbOther: RadioButton

    private val PICK_IMAGE_REQUEST = 1
    private var selectedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val btnSkip = findViewById<Button>(R.id.btnSkip)

        btnSkip.setOnClickListener {
            startActivity(Intent(this, GroupActivity::class.java))
        }

        // Initialize UI Elements
        ivProfileImage = findViewById(R.id.ivProfileImage)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnSaveDetails = findViewById(R.id.btnSaveDetails)

        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        etFatherName = findViewById(R.id.etFatherName)
        etMotherName = findViewById(R.id.etMotherName)
        etSpouseName = findViewById(R.id.etSpouseName)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        rbOther = findViewById(R.id.rbOther)

        btnSelectImage.setOnClickListener { openGallery() }
        btnSaveDetails.setOnClickListener { saveUserDetails() }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data?.data != null) {
            val uri = data.data
            ivProfileImage.setImageURI(uri)

            // Convert selected image into Bitmap
            val drawable = ivProfileImage.drawable
            if (drawable is BitmapDrawable) {
                selectedBitmap = drawable.bitmap
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun saveUserDetails() {
        val fullName = etFullName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val age = etAge.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val fatherName = etFatherName.text.toString().trim()
        val motherName = etMotherName.text.toString().trim()
        val spouseName = etSpouseName.text.toString().trim()

        if (fullName.isEmpty() || phone.isEmpty() || age.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = when {
            rbMale.isChecked -> "Male"
            rbFemale.isChecked -> "Female"
            else -> "Other"
        }

        val profileImageBase64 = selectedBitmap?.let { bitmapToBase64(it) } ?: ""

        val user = hashMapOf(
            "fullName" to fullName,
            "phone" to phone,
            "age" to age,
            "address" to address,
            "fatherName" to fatherName,
            "motherName" to motherName,
            "spouseName" to spouseName,
            "gender" to gender,
            "profileImage" to profileImageBase64
        )

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Details saved successfully!", Toast.LENGTH_SHORT).show()
                openGroupDetailsActivity()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openGroupDetailsActivity() {
        val intent = Intent(this, GroupActivity::class.java)
        intent.putExtra("userName", etFullName.text.toString()) // Pass user's name
        startActivity(intent)
        finish() // Close UserDetailsActivity
    }
}
