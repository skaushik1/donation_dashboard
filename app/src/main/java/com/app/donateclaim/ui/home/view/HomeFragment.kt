package com.app.donateclaim.Ui.home.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.donateclaim.R
import com.app.donateclaim.Ui.home.AdapterClass.ProductAdapterClass
import com.app.donateclaim.model.feedmodel
import com.app.donateclaim.databinding.FragmentHomeBinding
import java.util.ArrayList


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    var productAdapterClass: ProductAdapterClass? = null

    var feed = ArrayList<feedmodel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        productAdapterClass = ProductAdapterClass(requireContext()).apply {
            itemClick = { index, model ->
                val mainIntent = Intent(requireContext(), ProductClaimActivity::class.java)
                startActivity(mainIntent)
            }
        }
        binding.rvProduct.adapter = productAdapterClass
        setpost()
    }

    private fun setpost() {
        feed.add(feedmodel(R.drawable.car_new_3))
        feed.add(feedmodel(R.drawable.car_new_2))
        feed.add(feedmodel(R.drawable.care_new))

        productAdapterClass!!.updateProduct(feed)

    }


}