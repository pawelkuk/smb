package com.pjatk.pawelkuklinski.miniprojekt1

import android.R
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityMainBinding
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme);

        super.onCreate(savedInstanceState)
        val sp = getPreferences(Context.MODE_PRIVATE)
        val binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSaveChanges.setOnClickListener{
            setTheme(android.R.style.Theme);
        }
    }
}