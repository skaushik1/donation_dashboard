package com.app.donateclaim.Ui.main

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.app.donateclaim.base.BaseActivity
import com.app.donateclaim.R
import com.app.donateclaim.Ui.home.view.HomeFragment
import com.app.donateclaim.Ui.myProductUploads.view.MyUplodesFragment
import com.app.donateclaim.databinding.ActivityMainBinding
import com.app.ekapic.ui.viewpager.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() {
            val viewPagerAdapter =
                ViewPagerAdapter(
                    supportFragmentManager
                )
            viewPagerAdapter.addFragment(HomeFragment(), "")
            viewPagerAdapter.addFragment(MyUplodesFragment(), "")





            binding.viewpager.adapter = viewPagerAdapter
            binding.viewpager.currentItem = 0
            binding.viewpager.offscreenPageLimit = 2
            binding.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


            binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    binding.viewpager.currentItem = position
                    when (position) {
                        0 ->   binding.bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
                        1 ->   binding.bottomNavigationView.menu.findItem(R.id.GalleryFragment).isChecked = true
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })



    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.homeFragment -> {
                binding.viewpager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.GalleryFragment -> {
                binding.viewpager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

//    fun somting(data: String) {
//        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
//    }



}