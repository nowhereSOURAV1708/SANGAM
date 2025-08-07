package com.example.sangam01

// Represents an Admin with their details
data class Admin(
    val fullName: String,
    val email: String,
    val password: String,
    val contactNumber: String,
    val photoResId: Int,
    val qrCodeResId: Int,// Stores admin's image (drawable resource ID)
    val qrCodeContent: String // ✅ Stores the actual text encoded in the QR code
) {

}

// Represents a group with an admin and members
data class Group(
    val groupName: String,
    val admin: Admin,        // Admin details
    val members: List<Member>,
    val qrCodeContent: String // ✅ Stores the QR text for this group
)

// Represents an individual member of a group
data class Member(
    val fullName: String,
    val nickname: String?,
    val age: Int,
    val gender: String,
    val address: String,
    val contactNumber: String,
    val fatherName: String,
    val motherName: String,
    val spouseName: String?,
    val childrenNames: String?,
    val height: String,
    val weight: Int,
    val hairColor: String,
    val eyeColor: String,
    val identifyingMarks: String?,
    val clothingDescription: String,
    val bloodGroup: String?,
    val medicalConditions: String?,
    val emergencyContact: String,
    val relationToGroup: String?,
    val photoResId: Int, // Stores member’s image (drawable resource ID)
    val latitude: Double,           // ✅ Add this
    val longitude: Double           // ✅ Add this

)
