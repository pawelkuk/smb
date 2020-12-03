package com.pjatk.pawelkuklinski.miniprojekt1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(@PrimaryKey(autoGenerate = true) var id:Long=0, var name: String, var price:String, var quantity: Long, var isBought: Boolean)