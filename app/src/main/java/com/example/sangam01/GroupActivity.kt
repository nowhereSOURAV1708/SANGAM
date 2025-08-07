package com.example.sangam01

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GroupActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val userName = intent.getStringExtra("userName") ?: "User"

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val btnCreateGroup = findViewById<Button>(R.id.btnCreateGroup)
        val btnJoinGroup = findViewById<Button>(R.id.btnJoinGroup)
        val btngp = findViewById<Button>(R.id.btngotoGroup)

        tvGreeting.text = "Namaste, $userName!  \uD83D\uDE4F\uD83C\uDFFB "

        btnCreateGroup.setOnClickListener {
            // Open CreateGroupActivity (Implement this later)
            val intent = Intent(this, CreateGroupActivity::class.java)
            startActivity(intent)
        }

        btnJoinGroup.setOnClickListener {
            // Open JoinGroupActivity (Implement this later)
            //val intent = Intent(this, JoinGroupActivity::class.java)
            Toast.makeText(this, "in development mode!", Toast.LENGTH_SHORT).show()
            //startActivity(intent)
        }

        btngp.setOnClickListener {
            // Open JoinGroupActivity (Implement this later)
            val intent = Intent(this, GroupDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
