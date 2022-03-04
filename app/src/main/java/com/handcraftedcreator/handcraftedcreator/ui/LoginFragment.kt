package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import org.koin.android.ext.android.bind

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseManager = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.loginBtn.setOnClickListener(this)
        binding.loginForgot.setOnClickListener(this)
        binding.loginRegister.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v){
            binding.loginBtn -> {
                if (binding.loginEmail.text.toString().trim() != "" && binding.loginPassword.toString().trim() != "") {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        binding.loginEmail.text.toString().trim(),
                        binding.loginPassword.text.toString().trim()
                    )
                        .addOnSuccessListener {
                            firebaseManager.sellerData().get().addOnSuccessListener { doc ->
                                if (doc.exists()) {
                                    findNavController().navigate(R.id.productsFragment)
                                } else {

                                    findNavController().navigate(R.id.profileFragment)
                                }
                            }
                                .addOnFailureListener { findNavController().navigate(R.id.profileFragment) }

                        }
                        .addOnFailureListener {
                            findNavController().navigate(R.id.profileFragment)
                        }
                }
            }

            binding.loginRegister -> {
                findNavController().navigate(R.id.registerFragment)
            }

            binding.loginForgot -> {
                if (binding.loginEmail.text.toString().trim() != "") {
                    FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(binding.loginEmail.text.toString().trim())
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Email Sent", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}