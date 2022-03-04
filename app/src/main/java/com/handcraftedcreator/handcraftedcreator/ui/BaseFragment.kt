package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.handcraftedcreator.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.functions.FirebaseFunctions

open class BaseFragment : Fragment() {

    var auth = FirebaseAuth.getInstance()
    private lateinit var uid: String
    private lateinit var currentUser: FirebaseUser

    val functions = FirebaseFunctions.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (auth.currentUser != null){
            currentUser = auth.currentUser!!
            uid = auth.uid.toString()
        }else{
//            findNavController().navigate(R.id.loginFragment)
        }
    }
}