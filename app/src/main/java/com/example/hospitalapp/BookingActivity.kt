package com.example.hospitalapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.text.SimpleDateFormat
import java.util.*


class BookingActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var selectedSpinnerItem: String = ""
    var time:String = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val toolbar = findViewById<Toolbar>(R.id.tbKrl)
        toolbar.title = "Записаться"
        val doctors = arrayOf(
            "Хирург - Салихов Арсен Алимбекович",
            "Кардиолог - Омарова Аида Каримжановна",
            "Лор - Тилеубергенова Акмарал Жиенеевна"
        )

        val timeArray = arrayOf("09:00", "11:00", "14:00", "16:00")

        val spinner = findViewById<Spinner>(R.id.doc_spinner)
        val dateText = findViewById<EditText>(R.id.book_date)
        val text = findViewById<EditText>(R.id.editText)
        val btn_book = findViewById<MaterialButton>(R.id.book)
        val timeSpinner = findViewById<Spinner>(R.id.time)
        val adapterRmhsk: ArrayAdapter<CharSequence> =
            ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, doctors)
        adapterRmhsk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapterRmhsk)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                selectedSpinnerItem = parent.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val adapterJam: ArrayAdapter<CharSequence> =
            ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, timeArray)
        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.setAdapter(adapterJam)
        timeSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                time = parent.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val usersRef = database.getReference("book")
        val currentUser = mAuth.currentUser
        data class User(
            val doctor: String,
            val date: String,
            val time: String,
            val text: String,
            val name: String,
            val f_name: String,
            val userEmail: String,
        )



        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = dateFormat.format(calendar.time)
                dateText.setText(date)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )

        dateText.setOnClickListener {
            datePickerDialog.show()
        }
        val TAG = "users"
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val database = FirebaseDatabase.getInstance().getReference("users").child(userId)
        var name:String =""
        var f_name:String =""
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Обработка полученных данных
                if (dataSnapshot.exists()) {
                    val value = dataSnapshot.getValue<HashMap<String, Any>>()
                    if (value != null) {
                        f_name = value.get("f_name") as String
                        name = value.get("name") as String
                    }
                    Log.d(TAG, "Value is: $value")
                } else {
                    Log.d(TAG, "No data found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        btn_book.setOnClickListener {
            val date = dateText.text
            val textDescription = text.text
            if (selectedSpinnerItem.isNotEmpty() && date.isNotEmpty() && textDescription.isNotEmpty()){
                val user = User(
                    selectedSpinnerItem.toString(),
                    date.toString(),
                    time.toString(),
                    textDescription.toString(),
                    name,
                    f_name,
                    currentUser?.email.toString())
                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                val randomString = UUID.randomUUID().toString()
                usersRef.child(randomString).setValue(user)
                Toast.makeText(this, "Успешно забронировано!", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
            }
        }


    }
}