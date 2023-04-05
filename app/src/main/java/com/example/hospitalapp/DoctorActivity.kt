package com.example.hospitalapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class DoctorActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        val btn = findViewById<MaterialButton>(R.id.btn_book)
        btn.setOnClickListener {
            val i = Intent(this, BookingActivity::class.java)
            startActivity(i)
        }
    }
}