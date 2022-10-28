package com.app.donateclaim.Ui.myProductUploads.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.donateclaim.base.BaseFragment
import com.app.donateclaim.Ui.home.AdapterClass.ProductAdapterClass
import com.app.donateclaim.Ui.home.viewmodel.GetProductListViewModelClass
import com.app.donateclaim.Ui.main.MainActivity
import com.app.donateclaim.databinding.FragmentMyUplodesBinding
import com.app.donateclaim.base.BaseViewModelFactory
import com.app.donateclaim.model.ProductsItem
import com.app.donateclaim.helper.PrefData

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
        (activity as MainActivity)
        binding = FragmentMyUplodesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
//        productsItem.clear()
        callUploadProductApi()
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
                mainIntent.putExtra("title",model.title)
                mainIntent.putExtra("productId",model.id.toString())
                mainIntent.putExtra("descrption", model.description)
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
            if (it!=null) {
                if (it.data?.products?.isNotEmpty() == true) {
                    //categoryItem.clear()
                    productsItem = ArrayList(it.data.products)
                    binding.tvNoDataFound.visibility = View.GONE
                    productAdapterClass!!.updateProduct(productsItem)
                } else {
                    binding.tvNoDataFound.visibility = View.VISIBLE
                    productsItem = ArrayList()
                    productAdapterClass!!.updateProduct(productsItem)
                    //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                }
                getProductListViewModelClass.getAllProductResponse.postValue(null)
            }


        })

        getProductListViewModelClass.isLoading.observe(baseActivity) { isLoading ->
            if (isLoading) {
                baseActivity.showProgress(requireContext())
                binding.tvNoDataFound.visibility = View.GONE
            } else {
                baseActivity.hideProgress()
                binding.tvNoDataFound.visibility = View.VISIBLE
            }
        }
    }

    private fun callUploadProductApi() {
        getProductListViewModelClass.getAllProduct(userId!!,"1")
    }

    private fun setOnClick() {
      binding.ivAdd.setOnClickListener {
          val mainIntent = Intent(requireContext(), UploadProductActivity::class.java)
          startActivity(mainIntent)
      }
    }
}