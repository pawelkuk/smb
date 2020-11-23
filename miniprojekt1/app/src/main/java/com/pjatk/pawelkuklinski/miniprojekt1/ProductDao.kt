package com.pjatk.pawelkuklinski.miniprojekt1

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("select * from product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    fun add(product: Product)

    @Update
    fun edit(product: Product)

    @Delete
    fun remove(product: Product)

    @Query("delete from product")
    fun deleteAll()
}