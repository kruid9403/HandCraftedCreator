package com.handcraftedcreator.handcraftedcreator.ui.newProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentProductStandardDetailsBinding
import com.handcraftedcreator.handcraftedcreator.adapter.StandardOptionsAdapter
import com.handcraftedcreator.handcraftedcreator.model.Product
import com.handcraftedcreator.handcraftedcreator.model.StandardDetails
import com.handcraftedcreator.handcraftedcreator.ui.BaseFragment


class ProductStandardDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentProductStandardDetailsBinding
    private lateinit var adapter: StandardOptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductStandardDetailsBinding.inflate(layoutInflater)

        val product = arguments?.get("product") as Product
        product.productStandard.add(StandardDetails(attribute = "Weight", ArrayList()))
        product.productStandard.add(StandardDetails(attribute = "Length", ArrayList()))
        product.productStandard.add(StandardDetails(attribute = "Width", ArrayList()))
        product.productStandard.add(StandardDetails(attribute = "Height", ArrayList()))

        binding.standardRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = StandardOptionsAdapter(requireContext(), product = product)
        binding.standardRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

        binding.standardCategoryBtn.setOnClickListener {
            if (!binding.standardCategoryEt.text.equals("")){
                product.productStandard.add(
                    StandardDetails(
                        binding.standardCategoryEt.text.toString(),
                        ArrayList()
                    )
                )
                adapter.notifyItemRangeChanged(0, product.productStandard.size)
                binding.standardCategoryEt.setText("")
            }
        }

        binding.standardCategoryNext.setOnClickListener {
            val bundle = bundleOf(
                "product" to product
            )

            product.productStandard.forEach {
                if (it.attribute == "Weight" && it.detailsList?.size != 1){
                    Toast.makeText(requireContext(), "One value for weight is required", Toast.LENGTH_SHORT).show()
                }else if (it.attribute == "Length"  && it.detailsList?.size != 1){
                    Toast.makeText(requireContext(), "One value for length is required", Toast.LENGTH_SHORT).show()
                }else if (it.attribute == "Width"  && it.detailsList?.size != 1){
                    Toast.makeText(requireContext(), "One value for width is required", Toast.LENGTH_SHORT).show()
                }else if(it.attribute == "Height" && it.detailsList?.size != 1){
                    Toast.makeText(requireContext(), "One value for height is required", Toast.LENGTH_SHORT).show()
                }else{
                    findNavController().navigate(R.id.productCustomDetails, bundle)
                }
            }
        }

        return binding.root
    }
}