package com.app.donateclaim.Ui.home.AdapterClass


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.app.donateclaim.R
import com.app.donateclaim.databinding.CustomProductShellBinding
import com.app.donateclaim.databinding.CustomproductBinding
import com.app.donateclaim.helper.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class TutorialViewPagerAdapter(
    private val arrayList: ArrayList<Int>
) :
    PagerAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = container.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        //val binding: RowTutorialItemBinding =DataBindingUtil.inflate(inflater, R.layout.row_tutorial_item, container, false)
        val mView: View

        val binding: CustomProductShellBinding =
            CustomProductShellBinding.inflate(inflater)
        mView = binding.root
        val tutorialImage: Int

        when (arrayList[position]) {
            AppConstant.TutorialScreen.Welcome -> {
                tutorialImage = R.drawable.car_new_3
            }
            AppConstant.TutorialScreen.CertifiedCars -> {
                tutorialImage = R.drawable.ic_car4
            }
            else -> {
                tutorialImage = R.drawable.ic_car1
            }
        }


        Glide.with(container.context)
            .load(tutorialImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivProducts)

        container.addView(
            mView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return mView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }
}