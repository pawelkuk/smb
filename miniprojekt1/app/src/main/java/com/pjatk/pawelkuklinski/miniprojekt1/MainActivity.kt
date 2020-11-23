package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getPreferences(Context.MODE_PRIVATE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button1.setOnClickListener{
            binding.textView1.text = binding.editText1.text
            Toast.makeText(this, binding.editText1.text, Toast.LENGTH_SHORT).show()
        }

        binding.btProducts.setOnClickListener{
            val productIntent = Intent(this, ProductsActivity::class.java)
            startActivity(productIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.putString("tv_text", binding.editText1.text.toString())
        editor.apply()
    }
    override fun onStart(){
        super.onStart()
        binding.textView1.text = sp.getString("tv_text", "was nothing written")

    }

}