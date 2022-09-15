package com.app.donateclaim.Ui.myProductUploads.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.app.donateclaim.Ui.home.AdapterClass.TutorialViewModel
import com.app.donateclaim.Ui.home.AdapterClass.TutorialViewPagerAdapter
import com.app.donateclaim.databinding.FragmentProductDetailsBinding
import com.app.donateclaim.helper.BaseViewModelFactory
import java.util.ArrayList


class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var tutorialList: ArrayList<Int>
    lateinit var tutorialViewModel: TutorialViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()


    }

    /**
     * This method is used to initialize view for this screen
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {

        tutorialViewModel =
            ViewModelProvider(this, BaseViewModelFactory { TutorialViewModel() }).get(
                TutorialViewModel::class.java
            )


        tutorialList = tutorialViewModel.getTutorialList()
        val tutorialViewPagerAdapter = TutorialViewPagerAdapter(
            tutorialList
        )

        binding.vpProductsDetails.adapter = tutorialViewPagerAdapter

        //Attach viewPager with indicator
        binding.indicator.setViewPager(binding.vpProductsDetails)



        binding.vpProductsDetails.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {


            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


    /**
     * This method is used to set the previous item for tutorial
     */
    private fun onPrevious() {
        if (binding.vpProductsDetails.currentItem >= 0) {
            binding.vpProductsDetails.currentItem = (binding.vpProductsDetails.currentItem - 1)
        }
    }

    /**
     * This method is used to set the next item for tutorial
     */
    private fun onNext() {
        if (binding.vpProductsDetails.currentItem <= binding.vpProductsDetails.childCount + 1) {
            binding.vpProductsDetails.currentItem = (binding.vpProductsDetails.currentItem + 1)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}