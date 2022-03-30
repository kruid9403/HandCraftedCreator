package com.handcraftedcreator.handcraftedcreator.ui

import android.Manifest
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentProfileBinding
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import com.handcraftedcreator.handcraftedcreator.managers.ImageManager
import com.handcraftedcreator.handcraftedcreator.model.Seller

class ProfileFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private val firebaseManager = FirebaseManager()
    private val imageManager = ImageManager()
    private val REQUEST_IMAGE = 1
    private var imgUrl = ""
    private lateinit var seller: Seller
    private var bitmap = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888)

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        Glide.with(requireContext()).load(it).into(binding.profileImage)
        bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
        addPhotoToStorage(bitmap)
    }

    private fun addPhotoToStorage(it: Bitmap) {
        firebaseManager.profileImage().child(auth.uid.toString()).putFile(imageManager.bitmapToUri(it, requireContext())).addOnCompleteListener{
            firebaseManager.profileImage().child(auth.uid.toString()).downloadUrl.addOnCompleteListener { task ->
                imgUrl = task.result.toString()
            }
        }
    }

    val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        Glide.with(requireContext()).load(it).into(binding.profileImage)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        firebaseManager.sellerData().get().addOnSuccessListener {
            if (it.exists()){
                seller = it.toObject(Seller::class.java)!!
                Glide.with(requireContext()).load(seller.imgUrl).into(binding.profileImage)
                binding.profileName.setText(seller.name)
                binding.profileShopName.setText(seller.shopName)
                binding.profileShopDescription.setText(seller.description)
            }
        }
        binding.profileNextBtn.setOnClickListener(this)
        binding.profileImage.setOnClickListener(this)
        binding.profileRotateImg.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v){
            binding.profileNextBtn -> {
                if (!this::seller.isInitialized) {
                    seller = Seller(
                        id = auth.uid.toString(),
                        name = binding.profileName.text.toString().trim(),
                        imgUrl = imgUrl,
                        fcmToken = "",
                        rating = 0.0,
                        stripeAct = "",
                        description = binding.profileShopDescription.text.toString().trim(),
                        shipping = null,
                        email = auth.currentUser?.email!!
                    )
                }else{
                    if (imgUrl != ""){
                        seller.imgUrl = imgUrl
                    }
                    seller.name = binding.profileName.toString().trim()
                    seller.shopName = binding.profileShopName.toString().trim()
                    seller.description = binding.profileShopDescription.toString().trim()
                }

                firebaseManager.sellerData().set(seller).addOnSuccessListener { 
                    findNavController().navigate(R.id.addressFragment)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_SHORT).show()
                }
            }
            binding.profileImage -> {
                val d = AlertDialog.Builder(requireContext())
                d.setTitle("Get Image")
                d.setPositiveButton("Camera"){ d,i ->
                    cameraPermission.launch(Manifest.permission.CAMERA)
                }
                d.setNegativeButton("Gallery"){d,i ->
                    galleryPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                d.show()
            }
            binding.profileRotateImg -> {
                bitmap = rotateImage(bitmap,90)
                Glide.with(requireContext()).load(bitmap).into(binding.profileImage)
                addPhotoToStorage(bitmap)
            }
        }
    }
}