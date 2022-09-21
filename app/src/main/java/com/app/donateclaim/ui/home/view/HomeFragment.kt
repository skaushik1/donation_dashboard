package com.app.donateclaim.Ui.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.donateclaim.base.BaseFragment
import com.app.donateclaim.Ui.home.AdapterClass.ProductAdapterClass
import com.app.donateclaim.Ui.home.viewmodel.GetProductListViewModelClass
import com.app.donateclaim.Ui.main.MainActivity
import com.app.donateclaim.databinding.FragmentHomeBinding
import com.app.donateclaim.base.BaseViewModelFactory
import com.app.donateclaim.model.ProductsItem
import com.app.donateclaim.helper.PrefData
import java.util.ArrayList


class HomeFragment : BaseFragment() {


    private lateinit var binding: FragmentHomeBinding
    var productAdapterClass: ProductAdapterClass? = null
    lateinit var getProductListViewModelClass: GetProductListViewModelClass
    var userId:String?= null



    private var productsItem: ArrayList<ProductsItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_home, container, false)
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
                val mainIntent = Intent(requireContext(), ProductClaimActivity::class.java)
                mainIntent.putExtra("title",model.title)
                mainIntent.putExtra("productId",model.id.toString())
                Log.d("productId", model.id.toString())
                mainIntent.putExtra("descrption", model.description)
                startActivity(mainIntent)
            }
        }
        binding.rvProduct.adapter = productAdapterClass
        //setpost()
        callGetAllPostApi()
        setObserver()

    }

    private fun setObserver() {
        getProductListViewModelClass.getAllProductResponse.observe(baseActivity, Observer { it ->
            Log.e("callthis","---");

            if (it.data?.products?.isNotEmpty() == true) {
                //categoryItem.clear()
                productsItem.addAll(it.data.products)
                binding.tvNoDataFound.visibility = View.GONE
                productAdapterClass!!.updateProduct(productsItem)
            } else {
                binding.tvNoDataFound.visibility = View.VISIBLE
                //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            }

        })

        getProductListViewModelClass.isLoading.observe(baseActivity) { isLoading ->
            if (isLoading) {
                binding.tvNoDataFound.visibility = View.GONE
                baseActivity.showProgress(requireContext())
            } else {
                baseActivity.hideProgress()
                binding.tvNoDataFound.visibility = View.VISIBLE
            }
        }
    }

    private fun callGetAllPostApi() {
      getProductListViewModelClass.getAllProduct(userId!!,"0")
    }

}