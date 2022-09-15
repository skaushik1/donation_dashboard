package com.app.donateclaim.Ui.myProductUploads.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.donateclaim.BaseFragment
import com.app.donateclaim.R
import com.app.donateclaim.Ui.home.AdapterClass.ProductAdapterClass
import com.app.donateclaim.Ui.home.viewmodel.GetProductListViewModelClass
import com.app.donateclaim.model.feedmodel
import com.app.donateclaim.databinding.FragmentMyUplodesBinding
import com.app.donateclaim.helper.BaseViewModelFactory
import com.app.donateclaim.model.ProductsItem
import com.app.donateclaim.rxjava.PrefData
import java.util.ArrayList

class MyUplodesFragment : BaseFragment() {


    private lateinit var binding: FragmentMyUplodesBinding
    var productAdapterClass: ProductAdapterClass? = null

    var productsItem = ArrayList<ProductsItem>()
    lateinit var getProductListViewModelClass: GetProductListViewModelClass
    var userId:String?= null


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

        getProductListViewModelClass = ViewModelProvider(
            this,
            BaseViewModelFactory { GetProductListViewModelClass() })[GetProductListViewModelClass::class.java]


        userId= localPref.getStringPrefs(PrefData.UserId).toString()
        Log.d("userId", userId!!)




        productAdapterClass = ProductAdapterClass(requireContext()).apply {
            itemClick = { index, model ->
                val mainIntent = Intent(requireContext(), ProductDetailActivity::class.java)
                startActivity(mainIntent)
            }
        }
        binding.rvUploadeProduct.adapter = productAdapterClass
        callUploadProductApi()
        setObserver()
        //setpost()
        setOnClick()
    }

    private fun setObserver() {
        getProductListViewModelClass.getAllProductResponse.observe(baseActivity, Observer { it ->
            if (it.data?.products?.isNotEmpty() == true) {
                //categoryItem.clear()
                productsItem.addAll(it.data!!.products)
                binding.tvNoDataFound.visibility = View.GONE
                productAdapterClass!!.updateProduct(productsItem)
            } else {
                binding.tvNoDataFound.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            }

        })

        getProductListViewModelClass.isLoading.observe(baseActivity) { isLoading ->
            if (isLoading) {
                baseActivity.showProgress(requireContext())
            } else {
                baseActivity.hideProgress()
            }
        }
    }

    private fun callUploadProductApi() {
        getProductListViewModelClass.getAllProduct(userId!!,"1")
    }

    private fun setOnClick() {
      binding.ivAdd.setOnClickListener {
          val mainIntent = Intent(requireContext(), UploadPostActivity::class.java)
          startActivity(mainIntent)
      }
    }

//    private fun setpost() {
//        feed.add(feedmodel(R.drawable.car_new_3))
//        feed.add(feedmodel(R.drawable.care_new))
//
//        productAdapterClass!!.updateProduct(feed)
//
//    }


}