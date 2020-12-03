package com.pjatk.pawelkuklinski.miniprojekt1

class ProductRepo(val productDao: ProductDao) {
    fun getProducts() = productDao.getProducts()
    fun add(product: Product) = productDao.add(product)
    fun edit(product: Product) = productDao.edit(product)
    fun remove(product: Product) = productDao.remove(product)
    fun removeAll() = productDao.deleteAll()
    fun getProductById(id: Long) = productDao.getProductById(id)

}