package com.handcraftedcreator.handcraftedcreator.ui.newProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentProductCustomDetailsBinding
import com.handcraftedcreator.handcraftedcreator.adapter.CustomOptionsAdapter
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.model.Product
import com.handcraftedcreator.handcraftedcreator.model.ProductOptions
import com.handcraftedcreator.handcraftedcreator.model.StandardDetails
import org.koin.android.ext.android.bind

class ProductCustomDetails : Fragment() {

    private lateinit var binding: FragmentProductCustomDetailsBinding
    private lateinit var adapter: CustomOptionsAdapter
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductCustomDetailsBinding.inflate(layoutInflater)

        if (arguments != null){
            product = arguments?.get("product") as Product
        }

        binding.customRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CustomOptionsAdapter(
            context = requireContext(),
            product = product
        )
        binding.customRecycler.adapter = adapter

        binding.customCategoryBtn.setOnClickListener {
            if (!binding.customCategoryEt.text.equals("")){
                product.options.add(
                    ProductOptions(
                        binding.customCategoryEt.text.toString(),
                        ArrayList()
                    )
                )
                adapter.notifyItemRangeChanged(0, product.options.size)
                binding.customCategoryEt.setText("")
            }
        }

        binding.customCategoryNext.setOnClickListener {
            product.imgUrl.remove("")
            FirebaseManager().productData().add(product)
                .addOnCompleteListener {
                    findNavController().navigate(R.id.productsFragment)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }

        return binding.root
    }
}