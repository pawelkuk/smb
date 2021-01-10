package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityProductsBinding

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences("filename", Context.MODE_PRIVATE)
        val uid = intent.getStringExtra("userUid")
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()
        if (sp.getBoolean("isIrritationMode", false)) {
            binding.root.setBackgroundColor(Color.CYAN)
            binding.button.setBackgroundColor(Color.YELLOW)
            binding.btMainMenu.setBackgroundColor(Color.YELLOW)
        }
        val viewModel = ProductViewModel(application, FirebaseFirestore.getInstance(), uid)
        val adapter = ProductAdapter(viewModel, this, uid)
        viewModel.products.observe(this, Observer {
            it.let {
                adapter.setListProduct(it)
            }
        })
        viewModel.newProduct.observe(this, Observer {
            val broadcast = Intent("com.pjatk.pawelkuklinski.miniprojekt1.ADD_PRODUCT")
            broadcast.component = ComponentName(
                "com.pjatk.pawelkuklinski.miniproject2",
                "com.pjatk.pawelkuklinski.miniproject2.AddProductReceiver"
            )
            broadcast.putExtra("name", viewModel.newProduct.value?.name)
            broadcast.putExtra("id", viewModel.newProduct.value?.id)
            sendBroadcast(broadcast)
        })
        val btColor = sp.getString("color", null)
        if (btColor != null && !sp.getBoolean("isIrritationMode", false)){
            binding.button.setBackgroundColor(COLOR_MAPPER[btColor]!!)
            binding.btMainMenu.setBackgroundColor(COLOR_MAPPER[btColor]!!)
        }
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        binding.rvProductList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvProductList.adapter = adapter
        binding.button.setOnClickListener{
            val product = Product(
                    name = binding.etName.text.toString(),
                    price = binding.etPrice.text.toString(),
                    quantity = binding.etQuantity.text.toString().toLong(),
                    isBought = false,
                    id = null
            )
            viewModel.add(product)

        }
        binding.button.setOnLongClickListener {
            viewModel.removeAll()
            true
        }
        binding.btMainMenu.setOnClickListener {
            val mainMenuIntent = Intent(this, MainActivity::class.java)
            mainMenuIntent.putExtra("userUid", uid)
            startActivity(mainMenuIntent)
        }
    }
}