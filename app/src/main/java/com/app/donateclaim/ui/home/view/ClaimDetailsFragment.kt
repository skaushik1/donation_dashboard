package com.app.donateclaim.Ui.home.view

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
import com.app.donateclaim.databinding.FragmentClaimDetailsBinding
import com.app.donateclaim.helper.BaseViewModelFactory
import java.util.ArrayList


class ClaimDetailsFragment : Fragment() {
    private var _binding: FragmentClaimDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var tutorialList: ArrayList<Int>
    lateinit var tutorialViewModel: TutorialViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClaimDetailsBinding.inflate(inflater, container, false)
        return binding.root
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

        binding.vpTutorial.adapter = tutorialViewPagerAdapter

        //Attach viewPager with indicator
        binding.indicator.setViewPager(binding.vpTutorial)



        binding.vpTutorial.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        if (binding.vpTutorial.currentItem >= 0) {
            binding.vpTutorial.currentItem = (binding.vpTutorial.currentItem - 1)
        }
    }

    /**
     * This method is used to set the next item for tutorial
     */
    private fun onNext() {
        if (binding.vpTutorial.currentItem <= binding.vpTutorial.childCount + 1) {
            binding.vpTutorial.currentItem = (binding.vpTutorial.currentItem + 1)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}