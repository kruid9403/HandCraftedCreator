package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentProductsBinding
import com.handcraftedcreator.handcraftedcreator.adapter.ProductAdapter
import com.handcraftedcreator.handcraftedcreator.model.Product
import com.handcraftedcreator.handcraftedcreator.viewModel.ProductsViewModel

class ProductsFragment : BaseFragment(), View.OnClickListener, ProductAdapter.ProductClicked {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (auth.currentUser != null){
            // wtf
        }else{
            findNavController().navigate(R.id.loginFragment)
        }

        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(layoutInflater)

        binding.productFab.setOnClickListener(this)
        binding.productRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = ProductAdapter(requireContext(), this)
        binding.productRecycler.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner){products ->
            adapter.updateList(products)
        }

        viewModel.getProducts()
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v){
            binding.productFab ->{
                findNavController().navigate(R.id.newProductFragment)
            }
        }
    }

    override fun onProductClicked(product: Product) {
        val bundle = bundleOf(
            "product" to product
        )
        findNavController().navigate(R.id.productShopViewFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }
}