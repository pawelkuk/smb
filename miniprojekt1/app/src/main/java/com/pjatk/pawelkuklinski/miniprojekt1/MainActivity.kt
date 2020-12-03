package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getPreferences(Context.MODE_PRIVATE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btProducts.setOnClickListener{
            val productIntent = Intent(this, ProductsActivity::class.java)
            startActivity(productIntent)
        }
        binding.btOptions.setOnClickListener{
            val optionsIntent = Intent(this, OptionsActivity::class.java)
            startActivity(optionsIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.apply()
    }
    override fun onStart(){
        super.onStart()

    }

}