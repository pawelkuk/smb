package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityEditProductsBinding

class EditProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getLongExtra("productId", -1)
        if (id == (-1).toLong()) {
            Toast.makeText(this, "id not found", Toast.LENGTH_SHORT).show()
        }
        val viewModel = ProductViewModel(application)
        val product = viewModel.getProductbyId(id)

        binding.etEditName.text = Editable.Factory.getInstance().newEditable(product.name)
        binding.etEditPrice.text = Editable.Factory.getInstance().newEditable(product.price)
        binding.etEditQuantity.text = Editable.Factory.getInstance().newEditable(product.quantity.toString())
        binding.cbEditIsBought.isChecked = product.isBought

        binding.btSave.setOnClickListener{
            viewModel.edit(
                Product(
                    id = (id).toLong(),
                    name = binding.etEditName.text.toString(),
                    price = binding.etEditPrice.text.toString(),
                    quantity = binding.etEditQuantity.text.toString().toLong(),
                    isBought = binding.cbEditIsBought.isChecked
            ))
            val productListIntent = Intent(this, ProductsActivity::class.java)
            startActivity(productListIntent)
        }

        binding.btCancel.setOnClickListener{
            val productListIntent = Intent(this, ProductsActivity::class.java)
            startActivity(productListIntent)
        }

    }
}