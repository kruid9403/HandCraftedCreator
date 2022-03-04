package com.handcraftedcreator.handcraftedcreator.managers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.core.KoinComponent

class FirebaseManager(){

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance().reference

    fun customerDataLocation() = firestore.collection("customer").document(auth.uid.toString())

    fun productData() = firestore.collection("products")

    fun myProductQuery() = firestore.collection("products").whereEqualTo("creator", auth.uid.toString()).get()

    fun sellerData() = firestore.collection("sellers").document(auth.uid.toString())

    fun orderData() = firestore.collection("orders")

    fun profileImage() = storage.child("profile").child(auth.uid.toString())

    fun productImage() = storage.child("product")

    fun creatorStripeData() = firestore.collection("creatorStripeData").document(auth.uid.toString())

    fun categoryList() = firestore.collection("categories").document("categories")
}