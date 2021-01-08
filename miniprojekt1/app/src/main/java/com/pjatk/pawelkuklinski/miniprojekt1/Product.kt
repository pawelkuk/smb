package com.pjatk.pawelkuklinski.miniprojekt1


data class Product(var id:String?, var name: String, var price:String, var quantity: Long, var isBought: Boolean){
    override fun toString(): String {
        return "Product($id, $name, $price, $quantity, $isBought)"
    }

    fun toMap(): MutableMap<String, Any> {
        val product = hashMapOf<String, Any>()
        product["name"] = name
        product["price"] = price
        product["quantity"] = quantity
        product["isBought"] = isBought
        return product
    }
}