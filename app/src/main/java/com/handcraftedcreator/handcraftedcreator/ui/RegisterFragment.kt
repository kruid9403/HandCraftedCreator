package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentRegisterBinding
import org.koin.android.ext.android.bind

class RegisterFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.registerBtn.setOnClickListener(this)
        binding.registerLogin.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v){
            binding.registerBtn -> {
                auth.createUserWithEmailAndPassword(
                    binding.registerEmail.text.toString().trim(),
                    binding.registerPassword.text.toString().trim()
                )
                    .addOnSuccessListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            }
            binding.registerLogin -> {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }
}