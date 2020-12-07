package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityProductsBinding
import kotlinx.android.synthetic.main.list_element.view.*

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences("filename", Context.MODE_PRIVATE)
        if (sp.getBoolean("isIrritationMode", false)) {
            binding.root.setBackgroundColor(Color.CYAN)
            binding.button.setBackgroundColor(Color.YELLOW)
            binding.btMainMenu.setBackgroundColor(Color.YELLOW)
        }
        val viewModel = ProductViewModel(application)
        val adapter = ProductAdapter(viewModel, this)
        viewModel.allProducts.observe(this, Observer {
            it.let {
                adapter.setListProduct(it)
            }
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
                    isBought = false
            )
            viewModel.add(product)
            val broadcast = Intent("com.pjatk.pawelkuklinski.miniprojekt1.ADD_PRODUCT")
            broadcast.component = ComponentName(
                "com.pjatk.pawelkuklinski.miniproject2",
                "com.pjatk.pawelkuklinski.miniproject2.AddProductReceiver"
            )
            broadcast.putExtra("name", binding.etName.text.toString())
            broadcast.putExtra("id", viewModel.allProducts.value?.last()?.id?.plus(1))
            sendBroadcast(broadcast)
        }
        binding.button.setOnLongClickListener {
            viewModel.removeAll()
            true
        }
        binding.btMainMenu.setOnClickListener {
            val mainMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(mainMenuIntent)
        }
    }
}