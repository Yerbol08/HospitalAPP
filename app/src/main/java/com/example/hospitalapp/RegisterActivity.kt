package com.example.hospitalapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {
    // инициализация Firebase
    private val mAuth = FirebaseAuth.getInstance()
    // получение доступа к базе данных
    private val database = FirebaseDatabase.getInstance()
    private var sTanggal:kotlin.String? = null
    var newCalendar: Calendar = Calendar.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val f_nameTxt = findViewById<EditText>(R.id.reg_f_name)
        val nameTxt = findViewById<EditText>(R.id.reg_name)
        val emailTxt = findViewById<EditText>(R.id.reg_email)
        val addressTxt = findViewById<EditText>(R.id.reg_address)
        val workTxt = findViewById<EditText>(R.id.reg_work)
        val dateTxt = findViewById<EditText>(R.id.reg_date)
        val passwordTxt = findViewById<EditText>(R.id.reg_password)
        val btnReg = findViewById<Button>(R.id.btn_reg)
        val login = findViewById<Button>(R.id.ke_login)

        login.setOnClickListener {
            val i = Intent(
                this@RegisterActivity,
                LoginActivity::class.java
            )
            startActivity(i)
        }
        btnReg.setOnClickListener {
            val f_name = f_nameTxt.text
            val email = emailTxt.text
            val name = nameTxt.text
            val address = addressTxt.text
            val date = dateTxt.text
            val password = passwordTxt.text
            val work = workTxt.text

            val usersRef = database.getReference("users")

            if (f_name.isNotEmpty() && name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty() && date.isNotEmpty() && password.isNotEmpty() && work.isNotEmpty()){
                // создание нового пользователя
                mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            data class User(
                                val f_name: String,
                                val name: String,
                                val age: String,
                                val email: String,
                                val address: String,
                                val work: String
                            )

                            val user1 = User(f_name.toString(), name.toString(), date.toString(), email.toString(), address.toString(), work.toString())
                            val userId = FirebaseAuth.getInstance().currentUser!!.uid
                            usersRef.child(userId).setValue(user1)
                            val i = Intent(
                                this@RegisterActivity,
                                MainActivity::class.java
                            )
                            startActivity(i)

                        } else {
                            // регистрация провалена
                            Toast.makeText(this, "тіркеу қатесі", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }


        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = dateFormat.format(calendar.time)
                dateTxt.setText(date)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        dateTxt.setOnClickListener {
            datePickerDialog.show()
        }

    }

}