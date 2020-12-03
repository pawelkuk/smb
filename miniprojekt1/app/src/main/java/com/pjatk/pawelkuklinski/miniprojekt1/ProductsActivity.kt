package com.pjatk.pawelkuklinski.miniprojekt1

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
        val viewModel = ProductViewModel(application)
        val adapter = ProductAdapter(viewModel, this)
        viewModel.allProducts.observe(this, Observer {
            it.let {
                adapter.setListProduct(it)
            }
        })
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        binding.rvProductList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvProductList.adapter = adapter
        binding.button.setOnClickListener{
            viewModel.add(
                Product(
                    name = binding.etName.text.toString(),
                    price = binding.etPrice.text.toString(),
                    quantity = binding.etQuantity.text.toString().toLong(),
                    isBought = false
                ))
        }
        binding.button.setOnLongClickListener {
            viewModel.removeAll()
            true
        }

    }
}