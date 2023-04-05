package com.example.hospitalapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            val i = Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
            startActivity(i)
        }
        val btn_out = findViewById<MaterialButton>(R.id.out)
        btn_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val i = Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
            startActivity(i)
        }
    }
    fun profileMenu(v: View?) {
        val i = Intent(this, ProfileActivity::class.java)
        startActivity(i)
    }

    fun historyMenu(v: View?) {
        val i = Intent(this, HistoryActivity::class.java)
        startActivity(i)
    }
    fun bookdokter(v: View?) {
        val i = Intent(this, DoctorActivity::class.java)
        startActivity(i)
    }

    fun bookHotel(v: View?) {
        val i = Intent(this, BookingActivity::class.java)
        startActivity(i)
    }
}