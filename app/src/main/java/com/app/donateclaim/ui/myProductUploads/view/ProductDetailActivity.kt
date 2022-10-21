package com.app.donateclaim.Ui.myProductUploads.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.donateclaim.base.BaseActivity
import com.app.donateclaim.R
import com.app.donateclaim.databinding.ActivityProductDetailBinding

class ProductDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    var title:String?= null
    var descrption:String? = null
    var productId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.getStringExtra("title").toString()
        descrption = intent.getStringExtra("descrption").toString()
        productId = intent.getStringExtra("productId").toString()
        addFragment(ProductDetailsFragment())
        //addFragment(ProductDetailsFragment())
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction();
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("descrption",descrption)
        bundle.putString("productId",productId)
        fragment.arguments= bundle
        transaction.replace(R.id.fm_productDetails, fragment)
        transaction.commit()
    }
}