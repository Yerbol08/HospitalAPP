package com.example.hospitalapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar = findViewById<Toolbar>(R.id.tbProfile)
        toolbar.title = "Профиль"
        val name = findViewById<TextView>(R.id.lblName)
        val address = findViewById<TextView>(R.id.lblAddress)
        val work = findViewById<TextView>(R.id.lblWork)
        val email = findViewById<TextView>(R.id.lblEmail)
        val date = findViewById<TextView>(R.id.lblDate)
        val usersRef = mAuth.currentUser?.let { database.getReference("users").child(it.uid) }
        usersRef?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    name.text =  user.f_name +" "+ user.name
                    address.text = user.address
                    work.text = user.work
                    email.text = user.email
                    date.text = user.age
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок
            }
        })
    }

    data class User(
        val f_name: String,
        val name: String,
        val age: String,
        val email: String,
        val address: String,
        val work: String
    ){
        constructor ():this("","","", "", "", "")
    }
}