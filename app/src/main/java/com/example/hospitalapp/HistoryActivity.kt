package com.example.hospitalapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.adapter.Booking
import com.example.hospitalapp.adapter.BookingAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HistoryActivity : AppCompatActivity() {
    private lateinit var bookingAdapter:BookingAdapter

    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<Toolbar>(R.id.tbHistory)
        toolbar.title = "История"
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val database = FirebaseDatabase.getInstance().getReference("book")
        val dataList = mutableListOf<Booking>()
        database.get().addOnSuccessListener { dataSnapshot ->
            for (appointmentSnapshot in dataSnapshot.children) {
                val appointment = appointmentSnapshot.getValue(Booking::class.java)
                if (appointment != null && appointment.userEmail == currentUser?.email ) {
                    dataList.add(appointment)
                }
            }
            bookingAdapter = BookingAdapter(dataList)
            recyclerView.adapter = bookingAdapter
        }.addOnFailureListener { exception ->
            Log.e("users", "Error getting data", exception)
        }

    }


}


