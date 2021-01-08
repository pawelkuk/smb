package com.pjatk.pawelkuklinski.miniprojekt1

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class ProductViewModel(application: Application, val db: FirebaseFirestore): AndroidViewModel(
    application
) {
    var products: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var editProduct: MutableLiveData<Product> = MutableLiveData<Product>()
    var newProduct: MutableLiveData<Product> = MutableLiveData<Product>()

    init {
        db.collection("product").addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null){
                val documents = snapshot.documents
                val allProducts = ArrayList<Product>()
                documents.forEach{
                    val data = it.data
                    val product = Product(id=it.reference.id,
                        name = (data?.get("name") as String?)!!,
                        quantity = (data?.get("quantity") as Long?)!!,
                        price = (data?.get("price") as String?)!!,
                        isBought = (data?.get("isBought") as Boolean?)!!
                    )
                    allProducts.add(product)
                }
                products.value = allProducts
            }
        }
    }

    fun getProducts(){
        db.collection("product").get().addOnCompleteListener{
            if (it.isSuccessful){
                val documents = it.result!!
                val allProducts = ArrayList<Product>()
                documents.forEach{
                    val data = it.data
                    val product = Product(id=it.reference.id,
                        name = (data?.get("name") as String?)!!,
                        quantity = (data?.get("quantity") as Long?)!!,
                        price = (data?.get("price") as String?)!!,
                        isBought = (data?.get("isBought") as Boolean?)!!
                    )
                    allProducts.add(product)
                }
                products.value = allProducts
            } else {
                Log.w(TAG, "Could not fetch products")
            }
        }
    }
    fun add(product: Product) {
        db.collection("product").add(product.toMap()).addOnSuccessListener {
            Log.i(TAG, "Added $product")
            product.id = it.id
            newProduct.value = product
        }
        .addOnFailureListener{
            Log.w(TAG, "Could not add $product")
        }

    }
    fun edit(product: Product){
        val productIdRef: DocumentReference = db.collection("product").document(product.id!!)
        productIdRef.update(product.toMap()).addOnCompleteListener{
            if (it.isSuccessful){
                Log.i(
                    TAG, "Edited $product"
                )
            }
            else {
                Log.w(TAG, "Could not edit")
            }
        }
    }

    fun remove(product: Product){
        val productIdRef: DocumentReference = db.collection("product").document(product.id!!)
        productIdRef.delete().addOnCompleteListener { it ->
            if (it.isSuccessful) {
                Log.i(
                    TAG, "Removed $product"
                )
            }else {
                Log.w(TAG, "Could not remove")
            }
        }
    }


    fun removeAll() {
        db.collection("product").get().addOnCompleteListener{
            if (it.isSuccessful){
                val batch = db.batch()
                it.result!!.forEach { ref -> batch.delete(ref.reference) }
                batch.commit()
            }
        }
    }

    fun getProductById(id: String) {
        db.collection("product").document(id).get().addOnCompleteListener{
            if (it.isSuccessful){
                val data = it.result!!
                val product = Product(id=data.id,
                    name = (data?.get("name") as String?)!!,
                    quantity = (data?.get("quantity") as Long?)!!,
                    price = (data?.get("price") as String?)!!,
                    isBought = (data?.get("isBought") as Boolean?)!!
                )
                editProduct.value = product
                Log.i(TAG, "editProduct changed")
            }
            else {
                Log.w(TAG, "Could not change editProduct")
            }

        }

    }

}