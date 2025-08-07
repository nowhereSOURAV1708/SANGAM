package com.example.sangam01

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class ImageCompareActivity : AppCompatActivity() {

    private lateinit var ivSelectedImage: ImageView
    private lateinit var btnPickImage: Button
    private var selectedImageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_compare)

        ivSelectedImage = findViewById(R.id.ivSelectedImage)
        btnPickImage = findViewById(R.id.btnPickImage)

        btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data?.data != null) {
            selectedImageUri = data.data
            ivSelectedImage.setImageURI(selectedImageUri)

            selectedImageUri?.let { compareWithDrawablesBySize(it) }
        }
    }

    private fun compareWithDrawablesBySize(imageUri: Uri) {
        val selectedSizeKB = getImageSizeInKBFromUri(imageUri)

        for (group in DataStore.groups) {
            for (member in group.members) {
                val resId = member.photoResId
                val drawableSizeKB = getDrawableSizeInKB(resId)

                if (selectedSizeKB == drawableSizeKB) {
                    val intent = Intent(this, ContactActivity::class.java).apply {
                        putExtra("GROUP_NAME", group.groupName)
                        putExtra("ADMIN_NAME", group.admin.fullName)
                        putExtra("USER_NAME", member.fullName)
                        putExtra("USER_PHOTO", resId)
                    }
                    Toast.makeText(this, "Match found in ${group.groupName}!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    return
                }
            }
        }

        Toast.makeText(this, "No matching image found in drawable.", Toast.LENGTH_SHORT).show()
    }

    private fun getImageSizeInKBFromUri(uri: Uri): Int {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        return ((bytes?.size ?: 0) / 10000)
    }

    private fun getDrawableSizeInKB(resId: Int): Int {
        val inputStream: InputStream = resources.openRawResource(resId)
        val bytes = inputStream.readBytes()
        inputStream.close()
        return bytes.size / 10000
    }
}
