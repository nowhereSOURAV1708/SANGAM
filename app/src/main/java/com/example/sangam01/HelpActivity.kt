package com.example.sangam01

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var etUserName: EditText
    private lateinit var etAge: EditText
    private lateinit var etAddress: EditText
    private lateinit var etGender: EditText
    private lateinit var etGroupName: EditText
    private lateinit var etGroupAdmin: EditText
    private lateinit var etMemberNames: EditText
    private lateinit var btnSearch: Button

    private var selectedBitmap: Bitmap? = null
    private var selectedImageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)


        btnSelectImage = findViewById(R.id.btnSelectImage)
        etUserName = findViewById(R.id.etUserName)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        etGender = findViewById(R.id.etGender)
        etGroupName = findViewById(R.id.etGroupName)
        etGroupAdmin = findViewById(R.id.etGroupAdmin)
        etMemberNames = findViewById(R.id.etMemberNames)
        btnSearch = findViewById(R.id.btnSearch)

        btnSelectImage.setOnClickListener {
            val intent = Intent(this, ImageCompareActivity::class.java)
            startActivity(intent)

        }
        btnSearch.setOnClickListener {
            searchUserInDataStore()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data?.data != null) {
            selectedImageUri = data.data
            ivProfileImage.setImageURI(selectedImageUri)

            // Convert selected image into Bitmap
            val drawable = ivProfileImage.drawable
            if (drawable is BitmapDrawable) {
                selectedBitmap = drawable.bitmap
            }
        }
    }

    private fun searchUserInDataStore() {
        val userName = etUserName.text.toString().trim()
        val age = etAge.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val gender = etGender.text.toString().trim()
        val groupName = etGroupName.text.toString().trim()
        val groupAdmin = etGroupAdmin.text.toString().trim()

        val enteredAttributes = mapOf(
            "userName" to userName,
            "age" to age,
            "address" to address,
            "gender" to gender,
            "groupName" to groupName,
            "groupAdmin" to groupAdmin
        ).filterValues { it.isNotEmpty() } // Remove empty fields

        if (enteredAttributes.isEmpty()) {
            Toast.makeText(this, "Please enter at least one detail!", Toast.LENGTH_SHORT).show()
            return
        }

        var bestMatchGroup: Group? = null
        var bestMatchMember: Member? = null
        var highestMatchCount = 0

        for (group in DataStore.groups) {
            for (member in group.members) {
                var matchCount = 0

                // ✅ Strictly check member attributes
                if (member.fullName.equals(userName, ignoreCase = true)) matchCount++
                if (member.age.toString() == age) matchCount++
                if (member.address.contains(address, ignoreCase = true)) matchCount++
                if (member.gender.equals(gender, ignoreCase = true)) matchCount++

                // ✅ Ensure correct group and member selection
                if (matchCount > highestMatchCount) {
                    highestMatchCount = matchCount
                    bestMatchGroup = group
                    bestMatchMember = member
                }
            }
        }

        if (bestMatchGroup != null && bestMatchMember != null) {
            val intent = Intent(this, ContactActivity::class.java).apply {
                putExtra("GROUP_NAME", bestMatchGroup.groupName)
                putExtra("ADMIN_NAME", bestMatchGroup.admin.fullName)
                putExtra("USER_NAME", bestMatchMember.fullName)
                putExtra("USER_PHOTO", bestMatchMember.photoResId) // ✅ Correct photo now!
            }
            Toast.makeText(this, "User found in ${bestMatchGroup.groupName}!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        } else {
            Toast.makeText(this, "No matching user found!", Toast.LENGTH_SHORT).show()
        }
    }




}