package com.pjatk.pawelkuklinski.miniprojekt1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private val repo: ProductRepo
    val allProducts: LiveData<List<Product>>
    init {
        repo = ProductRepo(ProductDB.getDatabase(application)!!.getProductDao())
        allProducts = repo.getProducts()
    }
    fun getProducts() = repo.getProducts()
    fun add(product: Product) = repo.add(product)
    fun edit(product: Product) = repo.edit(product)
    fun remove(product: Product) = repo.remove(product)
    fun removeAll() = repo.removeAll()

}