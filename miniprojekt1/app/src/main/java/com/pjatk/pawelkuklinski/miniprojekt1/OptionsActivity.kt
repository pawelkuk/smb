package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences("filename", Context.MODE_PRIVATE)
        if (sp.getBoolean("isIrritationMode", false)) {
            binding.root.setBackgroundColor(Color.CYAN)
            binding.btSaveChanges.setBackgroundColor(Color.YELLOW)
        }
        val btColor = sp.getString("color", null)
        if (btColor != null && !sp.getBoolean("isIrritationMode", false))
            binding.btSaveChanges.setBackgroundColor(COLOR_MAPPER[btColor]!!)
        binding.swIsIrritationMode.isChecked = sp.getBoolean("isIrritationMode", false)
        binding.btSaveChanges.setOnClickListener{
            val editor = sp.edit()
            editor.putBoolean("isIrritationMode", binding.swIsIrritationMode.isChecked)
            editor.putString("color", binding.spChooseColor.selectedItem.toString())
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
