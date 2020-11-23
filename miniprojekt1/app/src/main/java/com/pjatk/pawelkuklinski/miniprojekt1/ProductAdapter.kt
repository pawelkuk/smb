package com.pjatk.pawelkuklinski.miniprojekt1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ListElementBinding
import java.text.FieldPosition

class ProductAdapter(val viewModel: ProductViewModel) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    var productList = emptyList<Product>()
    class ProductViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.tvId.text = productList[position].id.toString()
        holder.binding.tvProduct.text = productList[position].product
        holder.binding.tvVariety.text = productList[position].variety
        holder.binding.tvQuantity.text = productList[position].quantity.toString()
        holder.binding.root.setOnClickListener {
            viewModel.remove(productList[position])
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