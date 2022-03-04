package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentProductShopViewBinding
import com.handcraftedcreator.handcraftedcreator.adapter.ShopViewCustomAdapter
import com.handcraftedcreator.handcraftedcreator.adapter.ShopViewPhotoAdapter
import com.handcraftedcreator.handcraftedcreator.adapter.ShopViewStandardAdapter
import com.handcraftedcreator.handcraftedcreator.model.Product
import java.text.NumberFormat

class ProductShopViewFragment : Fragment(), ShopViewPhotoAdapter.PhotoClicked, ShopViewStandardAdapter.StandardClicked, ShopViewCustomAdapter.OptionsClicked {

    private lateinit var binding: FragmentProductShopViewBinding
    private lateinit var photoAdapter: ShopViewPhotoAdapter
    private lateinit var standardAdapter: ShopViewStandardAdapter
    private lateinit var customAdapter: ShopViewCustomAdapter

    private var product = Product()

    private var fullSize = false
    private var mainPhotoLocation = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductShopViewBinding.inflate(layoutInflater)

        if (arguments != null){
            product = arguments?.get("product") as Product
        }else{
            findNavController().navigate(R.id.productsFragment)
        }

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2

        binding.shopViewName.text = product.name
        binding.shopViewPrice.text = format.format(product.price)
        binding.shopViewQuantity.text = product.quantity.toString()

        Glide.with(requireContext()).load(product.imgUrl[0]).into(binding.shopViewMainImg)
        mainPhotoLocation = product.imgUrl[0]

        binding.shopViewImageRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        photoAdapter = ShopViewPhotoAdapter(requireContext(), this, product.imgUrl)
        binding.shopViewImageRecycler.adapter = photoAdapter
        binding.shopViewDescription.text = product.description

        binding.shopViewStandard.layoutManager = LinearLayoutManager(requireContext())
        standardAdapter = ShopViewStandardAdapter(requireContext(), this, product.productStandard)
        binding.shopViewStandard.adapter = standardAdapter

        binding.shopViewCustomize.layoutManager = LinearLayoutManager(requireContext())
        customAdapter = ShopViewCustomAdapter(requireContext(), this, product.options)
        binding.shopViewCustomize.adapter = customAdapter

        binding.shopViewMainImg.setOnClickListener {
            fullSize = !fullSize
            if (fullSize) {
                binding.shopViewFullImg.visibility = View.VISIBLE
                Glide.with(requireContext()).load(mainPhotoLocation).into(binding.shopViewFullImg)
            }
        }

        binding.shopViewFullImg.setOnClickListener {
            fullSize = !fullSize
            if (!fullSize){
                binding.shopViewFullImg.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onPhotoClicked(photo: String, position: Int) {
        Glide.with(requireContext()).load(photo).into(binding.shopViewMainImg)
        mainPhotoLocation = photo
    }

    override fun onStandardClicked(photo: String, position: Int) {

    }

    override fun onOptionClicked(photo: String, position: Int) {

    }
}