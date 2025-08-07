package com.example.sangam01

import android.util.Log

object DataStore {
    fun getGroupByCredentials(email: String, password: String): Group? {
        Log.d("DEBUG", "Searching for: Email = $email, Password = $password")

        for (group in groups) {
            Log.d("DEBUG", "Checking group: ${group.groupName}, Admin = ${group.admin.email}")
        }

        val result = groups.find { it.admin.email == email && it.admin.password == password }

        Log.d("DEBUG", "Result: ${result?.groupName ?: "No group found"}")

        return result
    }

    // Predefined groups with admins and members
    val groups: MutableList<Group> = mutableListOf()


    init {
        // ✅ Adding a sample group
        groups.addAll(
            listOf(
                Group(
                    groupName = "Group A",
                    admin = Admin(
                        fullName = "Sourav Sandilya",
                        email = "ss@gmail.com",
                        password = "1234",
                        contactNumber = "8002358953",
                        photoResId = R.drawable.sourav,
                        qrCodeResId = R.drawable.a,
                        qrCodeContent = "admin_sourav_1234" // ✅ QR content stored here// Admin photo in drawable
                    ),
                    members = listOf(
                        Member(
                            fullName = "SOURAV SANDILYA",
                            nickname = "ANSHU",
                            age = 20,
                            gender = "M",
                            address = "DEOGHAR",
                            contactNumber = "8002358953",
                            fatherName = "ARVIND KUMAR THAKUR ",
                            motherName = "NEELAM DEVI",
                            spouseName = null,
                            childrenNames = null,
                            height = "5'7\"",
                            weight = 84,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = null,
                            clothingDescription = "College Uniform",
                            bloodGroup = "A+",
                            medicalConditions = null,
                            emergencyContact = "9832203018",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.sourav ,
                            latitude = 22.5726,              // ✅ Kolkata coordinates
                            longitude = 88.3639
                        ),
                        Member(
                            fullName = "SABIR MALLICK",
                            nickname = "AYAN",
                            age = 20,
                            gender = "M",
                            address = "DURGAPUR",
                            contactNumber = "9832203018",
                            fatherName = "G. A. MALLICK",
                            motherName = "LAILA BEGAM",
                            spouseName = null,
                            childrenNames = null,
                            height = "5'6\"",
                            weight = 60,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = null,
                            clothingDescription = "College Uniform",
                            bloodGroup = "A+",
                            medicalConditions = null,
                            emergencyContact = "9832203018",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.sabir,
                            latitude = 22.5726,              // ✅ Kolkata coordinates
                            longitude = 88.3639
                        ),
                        Member(
                            fullName = "GORACHAND GHOSH",
                            nickname = "GORA",
                            age = 20,
                            gender = "M",
                            address = "JAMTARA",
                            contactNumber = "7029692707",
                            fatherName = "BAMAPADA GHOSH",
                            motherName = "RAJKUMARI GHOSH",
                            spouseName = "NA",
                            childrenNames = "NA",
                            height = "5'10\"",
                            weight = 65,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = "MOLE IN RIGHT SIDE OF LIP",
                            clothingDescription = "WHITE SHIRT AND BLUE PANTS",
                            bloodGroup = "A+",
                            medicalConditions = "NA",
                            emergencyContact = "6294540643",
                            relationToGroup = "BROTHER",
                            photoResId = R.drawable.gg,
                            latitude = 23.533440,              // ✅ Kolkata coordinates
                            longitude = 87.321930
                        ),
                        Member(
                            fullName = "AVINASH GHOSH",
                            nickname = "TAPLO",
                            age = 19,
                            gender = "M",
                            address = " DURGAPUR",
                            contactNumber = "8696940029",
                            fatherName = "RADHAMAN GHOSH",
                            motherName = "DIPALI GHOSH",
                            spouseName = "NA",
                            childrenNames = "NA",
                            height = "5'11\"",
                            weight = 82,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = "CUT MARK IN RIGHT EYEBROW",
                            clothingDescription = "OLIVE GREEN T-SHIRT, BLUE PANTS",
                            bloodGroup = "B+",
                            medicalConditions = "NA",
                            emergencyContact = "9660776079",
                            relationToGroup = "BROTHER",
                            photoResId = R.drawable.ag,
                            latitude = 23.533440,              // ✅ Kolkata coordinates
                            longitude = 87.321930
                        ),
                        Member(
                            fullName = "RITHIK YADAV",
                            nickname = "BINGO",
                            age = 22,
                            gender = "M",
                            address = "JHARIA, DHANBAD, JHARKHAND",
                            contactNumber = "9523123212",
                            fatherName = "RAM LAKHAN YADAV",
                            motherName = "NIRMALA DEVI",
                            spouseName = "NA",
                            childrenNames = "NA",
                            height = "5'3\"",
                            weight = 45,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = "MOLE IN BOTH HANDS",
                            clothingDescription = "WHITE SHIRT AND BROWN PANTS",
                            bloodGroup = "B+",
                            medicalConditions = "NA",
                            emergencyContact = "6205862141",
                            relationToGroup = "FRIEND",
                            photoResId = R.drawable.ritik,latitude = 23.533440,              // ✅ Kolkata coordinates
                            longitude = 87.321930
                        ),
                        Member(
                            fullName = "MOHIT KR. ROY",
                            nickname = "MOHAN",
                            age = 19,
                            gender = "M",
                            address = "FULJHORE, KALIGANJ, PASCHIM BURDWAN, WEST BENGAL",
                            contactNumber = "7643880991",
                            fatherName = "SANTOSH KR ROY",
                            motherName = "NEHA DEVI",
                            spouseName = "NA",
                            childrenNames = "NA",
                            height = "5'5\"",
                            weight = 48,
                            hairColor = "BLACK",
                            eyeColor = "BLACK",
                            identifyingMarks = "CUT MARK IN NOSE",
                            clothingDescription = "WHITE SHIRT AND BLUE PANTS",
                            bloodGroup = "O+",
                            medicalConditions = "NA",
                            emergencyContact = "9431595687",
                            relationToGroup = "FRIEND",
                            photoResId = R.drawable.mkr,
                            latitude = 23.533440,              // ✅ Kolkata coordinates
                            longitude = 87.321930
                        )
                    ), qrCodeContent = "group_a_5678" // ✅ QR content for group
                ),
                Group(
                    groupName = "Group B",
                    admin = Admin(
                        fullName = "CHANDRAJIT BANARJEE",
                        email = "cb@gmail.com",
                        password = "1234",
                        contactNumber = "9876543210",
                        photoResId = R.drawable.cb,
                        qrCodeResId = R.drawable.b,// Admin photo
                        qrCodeContent = "admin_swadhin_1234" // ✅ QR content stored here// Admin photo in drawable
                    ),
                    members = listOf(
                        Member(
                            fullName = "Chandrajit Banerjee",
                            nickname = "Rivu",
                            age = 21,
                            gender = "M",
                            address = "Singur",
                            contactNumber = "6289317043",
                            fatherName = " Bile Banerjee  ",
                            motherName = " Chandana Banerjee",
                            spouseName = null,
                            childrenNames = null,
                            height = "6'2\"",
                            weight = 85,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = null,
                            clothingDescription = "Black arsenal away jersey, black  trousers",
                            bloodGroup = "A+",
                            medicalConditions = null,
                            emergencyContact = "9832203018",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.cb,
                            latitude = 23.533440,              // ✅ Kolkata coordinates
                            longitude = 87.321930
                        ),

                        Member(
                            fullName = "NASIM ANJUM HOQUE",
                            nickname = "PRIMUSXIII",
                            age = 35,
                            gender = "M",
                            address = "NAZRUL PALLY BURDWAN 713101",
                            contactNumber = " 9734235817",
                            fatherName = "NASIMUL HOQUE",
                            motherName = "ANJUMAN ARA HOQUE",
                            spouseName = "",
                            childrenNames = "",
                            height = "6'",
                            weight = 89,
                            hairColor = "BROWN",
                            eyeColor = "BROWN",
                            identifyingMarks = "Left eyebrow mark",
                            clothingDescription = "Deep blue shirt and grey pants",
                            bloodGroup = "B+",
                            medicalConditions = "",
                            emergencyContact = "",
                            relationToGroup = "",
                            photoResId = R.drawable.nah,
                            latitude = 25.533440,              // ✅ Kolkata coordinates
                            longitude = 87.321930
                        ),
                        Member(
                            fullName = "Prasenjit Maji",
                            nickname = "",
                            age = 40,
                            gender = "M",
                            address = "BIDHANNAGAR, DURGAPUR, PASCHIM BARDHAMAN, WEST BENGAL",
                            contactNumber = "8116441403",
                            fatherName = "ANIL KUMAR MAJI",
                            motherName = "REKHA MAJI",
                            spouseName = "KUMKUM MAJI",
                            childrenNames = "DHRUBHANGI MAJI",
                            height = "5'5\"",
                            weight = 62,
                            hairColor = "",
                            eyeColor = "",
                            identifyingMarks = "Black-white mix beard, partially bald and fat",
                            clothingDescription = "",
                            bloodGroup = "B+",
                            medicalConditions = "Diabetes",
                            emergencyContact = "RKS",
                            relationToGroup = "Student",
                            photoResId = R.drawable.pm,
                            latitude = 22.536440,              // ✅ Kolkata coordinates
                            longitude = 87.321130
                        ),
                        Member(
                            fullName = "ADITI BISWAS",
                            nickname = "ADI",
                            age = 21,
                            gender = "F",
                            address = "BENACHITY DURGAPUR 713213",
                            contactNumber = "+917602532741, +919475445306",
                            fatherName = "SUJIT KUMAR BISWAS",
                            motherName = "DIPTI BISWAS",
                            spouseName = "",
                            childrenNames = "",
                            height = "5'1''",
                            weight = 50,
                            hairColor = "BLACK",
                            eyeColor = "BLACK",
                            identifyingMarks = "",
                            clothingDescription = "",
                            bloodGroup = "O+",
                            medicalConditions = "",
                            emergencyContact = "+919732322802",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.aditi,
                            latitude = 23.530440,              // ✅ Kolkata coordinates
                            longitude = 87.328930
                        ),
                        Member(
                            fullName = "MANISH KUMAR JHA",
                            nickname = "MANI",
                            age = 22,
                            gender = "M",
                            address = "DASPARA COLONY DURGAPUR 02",
                            contactNumber = "6203808151",
                            fatherName = "MANOJ KUMAR JHA",
                            motherName = "",
                            spouseName = "",
                            childrenNames = "",
                            height = "5.4",
                            weight = 57,
                            hairColor = "BLACK",
                            eyeColor = "BLACK",
                            identifyingMarks = "Cut mark on right hand",
                            clothingDescription = "White shirt, brown pant",
                            bloodGroup = "",
                            medicalConditions = "",
                            emergencyContact = "",
                            relationToGroup = "",
                            photoResId = R.drawable.mkj,
                            latitude = 23.5726,              // ✅ Kolkata coordinates
                            longitude = 88.3639
                        )
                    ), qrCodeContent = "group_a_5678" // ✅ QR content for group
                ), Group(
                    groupName = "Group C",
                    admin = Admin(
                        fullName = "Subhajit Pal",
                        email = "subho@gmail.com",
                        password = "s12345",
                        contactNumber = "9832485853",
                        photoResId = R.drawable.subho,  // Replace with actual drawable
                        qrCodeResId = R.drawable.c,    // Replace with actual drawable
                        qrCodeContent = "admin_subhajit_1234"
                    ),
                    members = listOf(

                        Member(
                            fullName = "Subhajit pal ",
                            nickname = "Subha",
                            age = 20,
                            gender = "M",
                            address = "Aungadpur, durgapur",
                            contactNumber = "9832485853",
                            fatherName = " Dilip Pal  ",
                            motherName = " Mou Pal",
                            spouseName = null,
                            childrenNames = null,
                            height = "5'9\"",
                            weight = 75,
                            hairColor = "BLACK",
                            eyeColor = "BROWN",
                            identifyingMarks = null,
                            clothingDescription ="White shirt",
                            bloodGroup = "b+",
                            medicalConditions = null,
                            emergencyContact = "9832203018",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.subho,
                            latitude = 22.5726,              // ✅ Kolkata coordinates
                            longitude = 88.3639
                        ),
                        Member(
                            fullName = "Manish Shivam",
                            nickname = "Shivam",
                            age = 22,
                            gender = "M",
                            address = "Andal, Pashim Burdhawan, West Bengal",
                            contactNumber = "9093931915",
                            fatherName = "Mukesh Kumar",
                            motherName = "Shikha Ranjan",
                            spouseName = null,
                            childrenNames = null,
                            height = "5'10\"",
                            weight = 85,
                            hairColor = "Black",
                            eyeColor = "Brown",
                            identifyingMarks = "None",
                            clothingDescription = "College Dress",
                            bloodGroup = "B+",
                            medicalConditions = "None",
                            emergencyContact = "Someone outside the group",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.ms,  // Replace with actual drawable
                            latitude = 23.503450,              // ✅ Kolkata coordinates
                            longitude = 87.621930
                        ),
                        Member(
                            fullName = "Suman Banerjee",
                            nickname = "Santu",
                            age = 46,
                            gender = "M",
                            address = "54 Feet Road, P.O. Benachity, Durgapur - 713213",
                            contactNumber = "9434535617",
                            fatherName = "Lt. R. N. Banerjee",
                            motherName = "Kanan Banerjee",
                            spouseName = "Madhurima Banerjee",
                            childrenNames = "Agniv Banerjee",
                            height = "6'0\"",
                            weight = 84,
                            hairColor = "Brownish Black",
                            eyeColor = "Brown",
                            identifyingMarks = "Stitch mark on forehead",
                            clothingDescription = "Striped shirt, Black jeans",
                            bloodGroup = "B+",
                            medicalConditions = "None",
                            emergencyContact = "RKS",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.suman,
                            latitude = 23.433440,              // ✅ Kolkata coordinates
                            longitude = 87.921930               // Replace with actual drawable
                        ),
                        Member(
                            fullName = "Astha Shankar",
                            nickname = "Avni",
                            age = 20,
                            gender = "F",
                            address = "Kusum Vihar, Dhanbad, Jharkhand",
                            contactNumber = "8102845848",
                            fatherName = "Dr. Jyoti Shankar Prasad",
                            motherName = "Seema Verna",
                            spouseName = null,
                            childrenNames = null,
                            height = "5'4\"",
                            weight = 55,
                            hairColor = "Black",
                            eyeColor = "Black",
                            identifyingMarks = "Birthmark on face",
                            clothingDescription = "White shirt",
                            bloodGroup = "B+",
                            medicalConditions = "None",
                            emergencyContact = "Someone outside the group",
                            relationToGroup = "Friend",
                            photoResId = R.drawable.`as`,
                            latitude = 21.533440,              // ✅ Kolkata coordinates
                            longitude = 84.321930// Replace with actual drawable
                        )
                    ),
                    qrCodeContent = "group_subhajit_5678"  // ✅ QR content for group
                )
            )
        )
        // ✅ Function to get group by admin email and password

    }
}

