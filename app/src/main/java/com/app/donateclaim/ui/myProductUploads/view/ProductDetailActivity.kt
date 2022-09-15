package com.app.donateclaim.Ui.myProductUploads.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.donateclaim.R
import com.app.donateclaim.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment(ProductDetailsFragment())
        //addFragment(ProductDetailsFragment())
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fm_productDetails, fragment)
        transaction.commit()
    }
}