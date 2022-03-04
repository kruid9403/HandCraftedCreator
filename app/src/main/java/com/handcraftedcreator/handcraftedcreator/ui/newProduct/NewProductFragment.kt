package com.handcraftedcreator.handcraftedcreator.ui.newProduct

import android.Manifest
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentNewProductBinding
import com.handcraftedcreator.handcraftedcreator.adapter.NewProductImageAdapter
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.managers.ImageManager
import com.handcraftedcreator.handcraftedcreator.model.Categories
import com.handcraftedcreator.handcraftedcreator.model.Product
import com.handcraftedcreator.handcraftedcreator.ui.BaseFragment

class NewProductFragment : BaseFragment(), View.OnClickListener, NewProductImageAdapter.NewProductImage {

    private lateinit var binding: FragmentNewProductBinding
    private lateinit var imgAdapter: NewProductImageAdapter

    private val firebaseManager = FirebaseManager()
    private val imageManager = ImageManager()
    private var available = true

    private lateinit var category: Categories
    private var selectedCategory: String = "none"

    private var bitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888)

    private var product = Product()
    private var imgPosition = 0

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
//        Glide.with(requireContext()).load(it).into(binding.newProductImg)
        bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
        addPhotoToStorage(bitmap)
    }

    private fun addPhotoToStorage(it: Bitmap) {
        Log.e("NewProd", product.id)
        if (product.id == null || product.id == ""){
            product.id = FirebaseManager().productData().document().id
        }
        Log.e("NewProd", product.id)
        firebaseManager.productImage()
            .child(auth.uid.toString())
            .child("${product.id}_$imgPosition")
            .putFile(imageManager.bitmapToUri(it, requireContext())).addOnCompleteListener{

            firebaseManager.productImage().child(auth.uid.toString()).child("${product.id}_$imgPosition")
                .downloadUrl.addOnCompleteListener {
                    product.imgUrl[imgPosition] = it.result.toString()
                    imgAdapter.notifyItemChanged(imgPosition)
                    binding.newProductProgressBar.visibility = View.GONE
            }
        }
    }

    val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
//        Glide.with(requireContext()).load(it).into(binding.newProductImg)
        bitmap = it
        addPhotoToStorage(it)
    }

    val galleryPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            galleryLauncher.launch("image/*")
        }else{
            Toast.makeText(requireContext(), "Gallery Permissions are required", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            cameraLauncher.launch()
        }else{
            Toast.makeText(requireContext(), "Permissions are required", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewProductBinding.inflate(layoutInflater)


        product.imgUrl.add("")
        product.imgUrl.add("")
        product.imgUrl.add("")
        product.imgUrl.add("")

        firebaseManager.categoryList()
            .get()
            .addOnSuccessListener {
                category = it.toObject(Categories::class.java)!!
                binding.newProductCategorySpinner.adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.spinner_text,
                    category.categories
                )
            }

        binding.newProductAddBtn.setOnClickListener(this)
        
        binding.newProductAvailable.isChecked = true
        
        binding.newProductAvailable.setOnCheckedChangeListener { buttonView, isChecked ->
            available = isChecked
        }

        binding.newProductCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategory = "none"
            }

        }

        binding.newProductImgRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        imgAdapter = NewProductImageAdapter(requireContext(), product.imgUrl, this)
        binding.newProductImgRecycler.adapter = imgAdapter

        return binding.root
    }

    override fun onClick(v: View?) {


        when(v){
            binding.newProductAddBtn -> {
                if (!binding.newProductName.text.equals("") && !binding.newProductPrice.text.equals("") &&
                    !binding.newProductDescription.text.equals("") && !binding.newProductBackOrder.text.equals("") &&
                    selectedCategory != "none"
                ) {
                    product.name = binding.newProductName.text.toString()
                    product.price = binding.newProductPrice.text.toString().toDouble()
                    product.description = binding.newProductDescription.text.toString()
                    product.backOrder = binding.newProductBackOrder.isSelected

                    product.id = FirebaseManager().productData().document().id
                    product.saleCount = 0
                    product.creator = auth.uid.toString()
                    product.visible = available
                    product.productStandard = ArrayList()
                    product.options = ArrayList()
                    product.category = selectedCategory
                    Log.e("NewProd", product.id + ";laksdjf;lskdjf")

                    val bundle = bundleOf(Pair("product", product))
                    findNavController().navigate(R.id.productStandardDetailsFragment, bundle)
                }

            }
        }
    }

    override fun getPhoto(position: Int) {
        if (binding.newProductProgressBar.visibility == View.GONE) {
            binding.newProductProgressBar.visibility = View.VISIBLE
            imgPosition = position
            val d = AlertDialog.Builder(requireContext())
            d.setTitle("Get Image")
            d.setPositiveButton("Camera") { d, i ->
                cameraPermission.launch(Manifest.permission.CAMERA)
            }
            d.setNegativeButton("Gallery") { d, i ->
                galleryPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            d.show()
        }
    }

    override fun rotatePhoto(position: Int) {
        if (binding.newProductProgressBar.visibility == View.GONE) {
            binding.newProductProgressBar.visibility = View.VISIBLE
            imgPosition = position
            bitmap = TransformationUtils.rotateImage(bitmap, 90)
            addPhotoToStorage(bitmap)
        }
    }
}