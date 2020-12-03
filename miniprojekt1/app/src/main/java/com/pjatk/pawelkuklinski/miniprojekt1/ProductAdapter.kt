package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ListElementBinding

class ProductAdapter(val viewModel: ProductViewModel, private val context: ProductsActivity) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var productList = emptyList<Product>()
    lateinit var inflater: LayoutInflater
    lateinit var parent: ViewGroup
    class ProductViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int ): ProductViewHolder {
        inflater = LayoutInflater.from(parent.context)
        this.parent = parent
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.tvName.text = productList[position].name.toString()
        holder.binding.tvPrice.text = productList[position].price.toString()
        holder.binding.tvQuantity.text = productList[position].quantity.toString()
        holder.binding.cbIsBought.isChecked = productList[position].isBought;
        holder.binding.root.setOnClickListener {
            val editProductIntent = Intent(context, EditProductsActivity::class.java)
            editProductIntent.putExtra("productId", productList[position].id)
            context.startActivity(editProductIntent)

        }

        holder.binding.root.setOnLongClickListener {
            viewModel.remove(productList[position])
            notifyDataSetChanged()
            true
        }

        holder.binding.cbIsBought.setOnClickListener{
            productList[position].isBought = holder.binding.cbIsBought.isChecked
            viewModel.edit(productList[position])
            notifyDataSetChanged()
        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setListProduct(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }
}