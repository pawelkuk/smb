package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Product::class], version = 2)
abstract class ProductDB: RoomDatabase(){
    abstract fun getProductDao(): ProductDao

    companion object {
        private var instance: ProductDB? = null

        fun getDatabase(context: Context): ProductDB? {
            if (instance!= null)
                return instance
            instance = Room.databaseBuilder(
                context,
                ProductDB::class.java,
                "productDB"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            return instance
        }
    }
}