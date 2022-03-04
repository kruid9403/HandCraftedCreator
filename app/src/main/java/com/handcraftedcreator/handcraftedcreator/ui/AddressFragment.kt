package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentAddressBinding
import com.example.handcraftedcreator.databinding.FragmentProfileBinding
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.model.Seller
import com.handcraftedcreator.handcraftedcreator.model.shipEngineRequest.ShipFrom
import org.koin.android.ext.android.bind

class AddressFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddressBinding
    private val firebaseManager = FirebaseManager()
    private lateinit var seller: Seller

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(layoutInflater)

        firebaseManager.sellerData().get().addOnSuccessListener {
            if (it.exists()){
                seller = Seller(it.id, it.data!!["name"].toString().trim(), it.data!!["shopName"].toString().trim(),
                it.data!!["imgUrl"].toString().trim(), it.data!!["fcmToken"].toString().trim(),
                it.data!!["rating"].toString().toDouble(), it.data!!["stripeAct"].toString().trim(),
                it.data!!["description"].toString().trim(), null, auth.currentUser?.email!!)
            }
        }

        binding.addressNextBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        val add1 = binding.addressLine1.text.toString().trim()
        val add2 = binding.addressLine2.text.toString().trim()
        val resIndicator = "yes"
        val city = binding.addressCity.text.toString().trim()
        val companyName = ""
        val countryCode = "US"
        val phone = binding.addressPhone.text.toString().trim()
        val zip = binding.addressZip.text.toString().trim()
        val state = binding.addressState.text.toString().trim()
        when(v){
            binding.addressNextBtn -> {
                if (add1 != "" && city != "" && phone != "" && zip != "" && state != ""){
                    val address = ShipFrom(add1, add2, resIndicator, city, companyName, countryCode, seller.name, phone, zip, state)

                    firebaseManager.sellerData().update("shipping", address)
                        .addOnSuccessListener { 
                            findNavController().navigate(R.id.stripeSetupFragment)
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Please Try Again", Toast.LENGTH_SHORT).show()
                        }
                }else{
                    Toast.makeText(requireContext(), "Phone, Address 1, City, State and Zip are Required", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}