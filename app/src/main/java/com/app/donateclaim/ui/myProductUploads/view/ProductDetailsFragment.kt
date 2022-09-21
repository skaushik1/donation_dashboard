package com.app.donateclaim.Ui.myProductUploads.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.app.donateclaim.base.BaseFragment
import com.app.donateclaim.Ui.home.AdapterClass.TutorialViewModel
import com.app.donateclaim.Ui.home.AdapterClass.ProductDetailsAdapter
import com.app.donateclaim.Ui.home.viewmodel.ProductDetailsViewModel
import com.app.donateclaim.Ui.myProductUploads.adapter.ClaimProductUserAdapter
import com.app.donateclaim.Ui.myProductUploads.viewmodel.DisabalPostViewmodel
import com.app.donateclaim.databinding.FragmentProductDetailsBinding
import com.app.donateclaim.base.BaseViewModelFactory
import com.app.donateclaim.model.ClaimedProductItem
import com.app.donateclaim.model.MediaItem
import java.util.ArrayList


class ProductDetailsFragment : BaseFragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private var productsDetails: ArrayList<MediaItem> = arrayListOf()
    private var claimedProductItem: ArrayList<ClaimedProductItem> = arrayListOf()
    lateinit var tutorialViewModel: TutorialViewModel
    lateinit var productDetailsViewModel: ProductDetailsViewModel
    lateinit var disabalPostViewmodel: DisabalPostViewmodel


    var productDetailsAdapter: ProductDetailsAdapter? = null
    var claimProductUserAdapter:ClaimProductUserAdapter?= null
    var productId:Int?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as ProductDetailActivity)
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
        onClick()

        productDetailsViewModel = ViewModelProvider(
            this,
            BaseViewModelFactory { ProductDetailsViewModel() })[ProductDetailsViewModel::class.java]

        disabalPostViewmodel = ViewModelProvider(
            this,
            BaseViewModelFactory { DisabalPostViewmodel() })[DisabalPostViewmodel::class.java]


        val bundle = arguments
        val title = bundle!!.getString("title")
        val descrption = bundle.getString("descrption")
        productId = bundle.getString("productId")!!.toInt()
        binding.tvProductName.text = title
        binding.tvDescrptionProduct.text = descrption
        productDetailsViewModel.ProductDetails(productId!!.toString(), 0)
        setObserver()


        productDetailsAdapter = ProductDetailsAdapter(productsDetails, requireActivity())
        binding.vpProductsDetails.adapter = productDetailsAdapter

        //Attach viewPager with indicator
        binding.indicator.setViewPager(binding.vpProductsDetails)


        //Attach viewPager with indicator
        //binding.indicator.setViewPager(binding.vpProductsDetails)

        binding.springDotsIndicator.attachTo(binding.vpProductsDetails);

        claimProductUserAdapter = ClaimProductUserAdapter(requireContext())
        binding.rvClaim.adapter = claimProductUserAdapter



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

    private fun onClick() {
       binding.btnDisablePost.setOnClickListener {
           callDisebalPostApi()
       }
    }

    private fun callDisebalPostApi() {
    disabalPostViewmodel.disabalPost(productId!!.toString(),"0")
    }

    private fun setObserver() {
        productDetailsViewModel.ProductDetailsResponse.observe(baseActivity, Observer { it ->
            if (it.media.isNotEmpty()) {
                //categoryItem.clear()
                productsDetails.addAll(it.media)
                //claimedProductItem.addAll(it.claimedProduct)
                productDetailsAdapter!!.setData(productsDetails)
                //claimProductUserAdapter!!.claimedProductItem(claimedProductItem)
                //binding.tvNoDataFound.visibility = View.GONE
            } else {
                //binding.tvNoDataFound.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            }

            if (it.claimedProduct.isNotEmpty()){
                claimedProductItem.addAll(it.claimedProduct)
                claimProductUserAdapter!!.claimedProductItem(claimedProductItem)
                binding.tvNoDataFound.visibility = View.GONE
            }else{
                binding.tvNoDataFound.visibility = View.VISIBLE
            }

        })

        disabalPostViewmodel.disablepost.observe(baseActivity, Observer { it ->
            if (it.status=="1") {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                baseActivity.finish()
            } else {
                //binding.tvNoDataFound.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            }

            disabalPostViewmodel.isLoading.observe(baseActivity) { isLoading ->
                if (isLoading) {
                    baseActivity.showProgress(requireContext())
                } else {
                    baseActivity.hideProgress()
                }
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