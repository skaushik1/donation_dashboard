package com.app.donateclaim.Ui.myProductUploads.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.donateclaim.R
import com.app.donateclaim.Ui.home.AdapterClass.ProductAdapterClass
import com.app.donateclaim.model.feedmodel
import com.app.donateclaim.databinding.FragmentMyUplodesBinding
import java.util.ArrayList

class MyUplodesFragment : Fragment() {


    private lateinit var binding: FragmentMyUplodesBinding
    var productAdapterClass: ProductAdapterClass? = null

    var feed = ArrayList<feedmodel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_uplodes, container, false)
        binding = FragmentMyUplodesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        productAdapterClass = ProductAdapterClass(requireContext()).apply {
            itemClick = { index, model ->
                val mainIntent = Intent(requireContext(), ProductDetailActivity::class.java)
                startActivity(mainIntent)
            }
        }
        binding.rvUploadeProduct.adapter = productAdapterClass
        setpost()
        setOnClick()
    }

    private fun setOnClick() {
      binding.ivAdd.setOnClickListener {
          val mainIntent = Intent(requireContext(), UploadPostActivity::class.java)
          startActivity(mainIntent)
      }
    }

    private fun setpost() {
        feed.add(feedmodel(R.drawable.car_new_3))
        feed.add(feedmodel(R.drawable.care_new))

        productAdapterClass!!.updateProduct(feed)

    }


}