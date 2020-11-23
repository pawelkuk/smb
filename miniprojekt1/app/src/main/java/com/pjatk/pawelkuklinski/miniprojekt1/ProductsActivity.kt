package com.pjatk.pawelkuklinski.miniprojekt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.os.IResultReceiver
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityProductsBinding

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ProductViewModel(application)
        val adapter = ProductAdapter(viewModel)
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
                    product = binding.etProduct.text.toString(),
                    variety = binding.etVariant.text.toString(),
                    quantity = binding.etQuantity.text.toString().toLong()
                ))
        }
        binding.button.setOnLongClickListener{
            viewModel.removeAll()
            true
        }

    }
}