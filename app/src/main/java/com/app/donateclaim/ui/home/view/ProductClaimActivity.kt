package com.app.donateclaim.Ui.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.donateclaim.R
import com.app.donateclaim.Ui.home.view.ClaimDetailsFragment
import com.app.donateclaim.databinding.ActivityProductClaimBinding

class ProductClaimActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductClaimBinding
    var title:String?= null
    var descrption:String? = null
    var productId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_claim)
        binding = ActivityProductClaimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.getStringExtra("title").toString()
        descrption = intent.getStringExtra("descrption").toString()
        productId = intent.getStringExtra("productId").toString()
        addFragment(ClaimDetailsFragment())

        //addFragment(ProductDetailsFragment())


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction();
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("descrption",descrption)
        bundle.putString("productId",productId)
        fragment.arguments= bundle
        transaction.replace(R.id.tutorialFrameLayout, fragment)
        transaction.commit()
    }
}