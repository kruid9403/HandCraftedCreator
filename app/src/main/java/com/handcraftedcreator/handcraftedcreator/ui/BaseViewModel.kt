package com.handcraftedcreator.handcraftedcreator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.model.Seller
import com.google.firebase.auth.FirebaseAuth

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    val firebaseManager = FirebaseManager()
    val auth = FirebaseAuth.getInstance()

    val seller by lazy { MutableLiveData<Seller>() }

    init {
        firebaseManager.sellerData()
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    seller.postValue(Seller(it.data!!["id"].toString(),
                        it.data!!["name"].toString(),
                        it.data!!["shopName"].toString(),
                        it.data!!["imgUrl"].toString(),
                        it.data!!["fcmToken"].toString(),
                        it.data!!["rating"].toString().toDouble(),
                        it.data!!["stripeAct"].toString(),
                        it.data!!["description"].toString(),
                        null,
                        it.data!!["email"].toString()))

                }
            }
    }


}