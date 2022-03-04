package com.handcraftedcreator.handcraftedcreator.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.model.Categories
import com.handcraftedcreator.handcraftedcreator.model.Product
import com.handcraftedcreator.handcraftedcreator.model.Seller
import com.handcraftedcreator.handcraftedcreator.ui.BaseViewModel

class ProductsViewModel(application: Application): BaseViewModel(application) {

    val products by lazy { MutableLiveData<ArrayList<Product>>()}
    val loading by lazy { MutableLiveData<String>() }

    fun setProduct(product: Product){
        firebaseManager.productData().add(product)
            .addOnSuccessListener {
                loading.postValue("Success")
            }
            .addOnFailureListener {
                loading.postValue(it.localizedMessage)
            }
    }

    fun getProducts(){
        var prodList = ArrayList<Product>()
        firebaseManager.myProductQuery()
            .addOnSuccessListener { docs ->
                docs.documents.map {
                    val product = it.toObject(Product::class.java)
                    product?.imgUrl?.removeAll(arrayListOf(""))
                    prodList.add(product!!)
                }
                products.postValue(prodList)
            }.addOnFailureListener {
                Log.e("ProdVM", it.localizedMessage)
            }
    }

    fun getCategories(){
        var categoryList = Categories()

        firebaseManager.categoryList()
            .get()
            .addOnSuccessListener {
                categoryList = it.toObject(Categories::class.java)!!
            }
    }
}