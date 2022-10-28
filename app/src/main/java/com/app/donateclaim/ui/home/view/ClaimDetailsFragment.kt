package com.app.donateclaim.Ui.home.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.app.donateclaim.Ui.home.viewmodel.ClaimProductViewModelClass
import com.app.donateclaim.Ui.home.viewmodel.ProductDetailsViewModel
import com.app.donateclaim.databinding.FragmentClaimDetailsBinding
import com.app.donateclaim.base.BaseViewModelFactory
import com.app.donateclaim.model.MediaItem
import com.app.donateclaim.helper.PrefData
import java.util.ArrayList


class ClaimDetailsFragment : BaseFragment() {
    private var _binding: FragmentClaimDetailsBinding? = null
    private val emailPattern = Patterns.EMAIL_ADDRESS

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    //var tutorialList: ArrayList<MediaItem>

    private var productsDetails: ArrayList<MediaItem> = arrayListOf()
    lateinit var tutorialViewModel: TutorialViewModel
    lateinit var productDetailsViewModel: ProductDetailsViewModel
    lateinit var claimProductViewModelClass: ClaimProductViewModelClass


    var productDetailsAdapter: ProductDetailsAdapter? = null
    var productId:Int?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as ProductClaimActivity)
        _binding = FragmentClaimDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setOnClick()


        productDetailsViewModel = ViewModelProvider(
            this,
            BaseViewModelFactory { ProductDetailsViewModel() })[ProductDetailsViewModel::class.java]

        claimProductViewModelClass = ViewModelProvider(
            this,
            BaseViewModelFactory { ClaimProductViewModelClass() })[ClaimProductViewModelClass::class.java]



        val bundle = arguments
        val title = bundle!!.getString("title")
        val descrption= bundle.getString("descrption")
        productId= bundle.getString("productId")!!.toInt()
        binding.tvProductName.text=title
        binding.tvDescrptionProduct.text=descrption
        productDetailsViewModel.ProductDetails(productId!!.toString(), 1)
        setObserver()



    }

    private fun setOnClick() {
       binding.btnSubmit.setOnClickListener {
           callClaimProdctApi()
       }
    }

    private fun callClaimProdctApi() {
        if (isValidData(
                name = binding.etFullName.text.toString(),
                email = binding.etEmail.text.toString(),
                phoneNo = binding.etPhoneNo.text.toString(),

            )
        ) {

            val name = binding.etFullName.text.toString()
            val email=binding.etEmail.text.toString()
            val phone=binding.etPhoneNo.text.toString()

            val userId= localPref.getStringPrefs(PrefData.UserId).toString()
//            Log.d("Claim produect ID",userId)
            claimProductViewModelClass.claimProductApi(userId, product_id = productId!!.toInt().toString(),
               name, email, phone
            )
        }

    }

    private fun isValidData(name: String, email: String, phoneNo: String): Boolean {
        val validUserName: Boolean
        var validEmail: Boolean
        val validPhoneNo: Boolean

        if (name.isEmpty()) {
            binding.etFullName.error = "Enter Your Full Name"
            validUserName = false
        } else {
            validUserName = true
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Enter Your Email"
            validEmail = false
        } else {
            validEmail = emailPattern.matcher(email).matches()
            if (!validEmail) {
                binding.etEmail.error ="Email is not valid"
            } else {
                validEmail = true
            }
        }

        if (phoneNo.isEmpty()) {
            binding.etPhoneNo.error = "Enter Your PhoneNo"
            validPhoneNo = false
        } else {
            validPhoneNo = true
        }

        if (validUserName) {
            binding.etFullName.error = null
        }

        if (validEmail) {
            binding.etEmail.error = null
        }

        if (validPhoneNo) {
            binding.etPhoneNo.error = null
        }

        return  validUserName && validEmail && validPhoneNo
    }

    private fun setObserver() {
        productDetailsViewModel.ProductDetailsResponse.observe(baseActivity, Observer { it ->
            if (it.media.isNotEmpty()) {
                //categoryItem.clear()
                productsDetails.addAll(it.media)
                productDetailsAdapter!!.setData(productsDetails)
                //binding.tvNoDataFound.visibility = View.GONE
                //productAdapterClass!!.updateProduct(productsItem)

            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            }

        })

        productDetailsViewModel.isLoading.observe(baseActivity) { isLoading ->
            if (isLoading) {
                baseActivity.showProgress(requireContext())
            } else {
                baseActivity.hideProgress()
            }
        }



        claimProductViewModelClass.claimProduct.observe(baseActivity, Observer { it ->
            if (it.status=="1") {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                requireActivity().finish()
                //categoryItem.clear()
                //binding.tvNoDataFound.visibility = View.GONE
                //productAdapterClass!!.updateProduct(productsItem)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            }

        })

        claimProductViewModelClass.isLoading.observe(baseActivity) { isLoading ->
            if (isLoading) {
                baseActivity.showProgress(requireContext())
            } else {
                baseActivity.hideProgress()
            }
        }


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


        //tutorialList = tutorialViewModel.getTutorialList()

        productDetailsAdapter = ProductDetailsAdapter(productsDetails,requireActivity())
        binding.vpTutorial.adapter = productDetailsAdapter

        //Attach viewPager with indicator
        binding.springDotsIndicator.attachTo(binding.vpTutorial)




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