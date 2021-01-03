package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityLoginBinding
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Logged it successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java).also {
                        it.putExtra(
                            "user",
                            auth.currentUser?.email
                        )
                    })
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btLogin.setOnLongClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Registration error", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
}