package com.handcraftedcreator.handcraftedcreator.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.handcraftedcreator.R
import com.example.handcraftedcreator.databinding.FragmentStripeSetupBinding
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager
import org.koin.android.ext.android.bind


class StripeSetupFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentStripeSetupBinding
    private val firebaseManager = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStripeSetupBinding.inflate(layoutInflater)

        binding.stripeSetupBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v){
            binding.stripeSetupBtn -> {
                Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
                connectStripe()
            }
        }
    }

    fun connectStripe() {
        val userData = hashMapOf(
            "uid" to auth.uid.toString()
        )
        functions.getHttpsCallable("setUpStripeCreator").call(userData)
            .addOnSuccessListener {
                setStripeAccountListener()

            }
            .addOnFailureListener {
                Log.e("StripeSetupFragment", it.toString())
            }
    }

    private fun setStripeAccountListener() {
        firebaseManager.creatorStripeData().addSnapshotListener { value, error ->
            if (error != null){
                Log.e("SettingsViewModel", error.toString())
            }
            if (value != null){
                Log.e("SettingsViewModel", value.get("id").toString().trim())

                val accountNumber = hashMapOf(
                    "accountNumber" to value.get("id").toString(),
                    "uid" to auth.uid.toString().trim()
                )
                functions.getHttpsCallable("stripeAccountLink").call(accountNumber).addOnSuccessListener {
                    val info = it.data as HashMap<*,*>
                    Log.e("SettingsViewModelHASH", info.toString())
                    val link = info.get("url").toString().trim()
                    binding.stripeOnboardingWebview.visibility = View.VISIBLE
                    binding.stripeText.visibility = View.GONE
                    binding.stripeSetupBtn.visibility = View.GONE

                    binding.stripeOnboardingWebview.webViewClient = object : WebViewClient(){
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            Log.e("StripeSetupFrag", url.toString())

                            if (url.toString() == "https://handcraftedmarket.wordpress.com/"){
                                findNavController().navigate(R.id.productsFragment)
                            }
                        }
                    }
                    binding.stripeOnboardingWebview.loadUrl(link)
                    binding.stripeOnboardingWebview.settings.javaScriptEnabled = true
                }
            }
        }
    }
}