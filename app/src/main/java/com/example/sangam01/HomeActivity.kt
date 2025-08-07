package com.example.sangam01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnFillDetails = findViewById<Button>(R.id.btnFillDetails)
        val btnHelpUser = findViewById<Button>(R.id.btnHelpUser)
        val btngotogp = findViewById<Button>(R.id.btngotogp)

        tvWelcome.text = "Welcome"

        btnFillDetails.setOnClickListener {
            startActivity(Intent(this, UserDetailsActivity::class.java))
            Toast.makeText(this, "Fill Details button pressed", Toast.LENGTH_SHORT).show()
        }

        btnHelpUser.setOnClickListener {
            // Define action for Help User button
            startActivity(Intent(this, HelpActivity::class.java))
        }

        btngotogp.setOnClickListener {
            startActivity(Intent(this, GroupDetailsActivity::class.java))
        }
    }
}
