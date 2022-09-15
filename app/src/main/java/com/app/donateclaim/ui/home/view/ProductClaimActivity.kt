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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_claim)
        binding = ActivityProductClaimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment(ClaimDetailsFragment())
        //addFragment(ProductDetailsFragment())
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.tutorialFrameLayout, fragment)
        transaction.commit()
    }
}