package com.example.hospitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtUsername = findViewById<EditText>(R.id.email)
        val txtPassword = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.masuk)
        val btnRegister = findViewById<Button>(R.id.ke_daftar)

        btnLogin.setOnClickListener {

            // Get username, password from EditText
            val email = txtUsername.text
            val password = txtPassword.text
            if (email.isNotEmpty() && password.isNotEmpty()){
                // вход пользователя
                mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // вход успешен
                            val user = mAuth.currentUser
                            val i = Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                            startActivity(i)
                            // добавьте код для перехода на следующий экран
                        } else {
                            // вход провален
                            Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

        btnRegister.setOnClickListener {
            val i = Intent(
                this@LoginActivity,
                RegisterActivity::class.java
            )
            startActivity(i)
        }
    }
}