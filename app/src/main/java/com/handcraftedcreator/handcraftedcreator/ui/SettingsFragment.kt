package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentSettingsBinding
import okhttp3.internal.wait

class SettingsFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        binding.settingsLogOut.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v){
            binding.settingsLogOut -> {
                auth.signOut()
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }
}