package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("filename", Context.MODE_PRIVATE)

        binding.etUser.text = intent.getStringExtra("user")
        val id = intent.getStringExtra("userUid")
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
        if (sp.getBoolean("isIrritationMode", false)) {
            binding.root.setBackgroundColor(Color.CYAN)
            binding.btOptions.setBackgroundColor(Color.YELLOW)
            binding.btProducts.setBackgroundColor(Color.YELLOW)
        }
        binding.btProducts.setOnClickListener{
            val productIntent = Intent(this, ProductsActivity::class.java)
            productIntent.putExtra("userUid", id)
            startActivity(productIntent)
        }
        binding.btOptions.setOnClickListener{
            val optionsIntent = Intent(this, OptionsActivity::class.java)
            startActivity(optionsIntent)
        }
        val btColor = sp.getString("color", null)
        if (btColor != null && !sp.getBoolean("isIrritationMode", false)) {
            binding.btOptions.setBackgroundColor(COLOR_MAPPER[btColor]!!)
            binding.btProducts.setBackgroundColor(COLOR_MAPPER[btColor]!!)
        }
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.apply()
    }
}