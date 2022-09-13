package com.app.donateclaim

import android.content.Context
import android.view.ViewGroup
import java.util.*

/**
 * Created by c210
 * on 11/02/19.
 */
abstract class BaseFragmentPagerAdapter
//    private Map<Integer, Fragment> mCurrentFragmentList = new LinkedHashMap<>();

/**
 * Instantiates a new Base fragment pager adapter.
 *
 * @param fm      the fm
 * @param context the mContext
 */
(fm: androidx.fragment.app.FragmentManager,
 /**
  * Gets mContext.
  *
  * @return the mContext
  */
 open val context: Context) : androidx.fragment.app.FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    /**
     * Gets mList.
     *
     * @return ListArray<FragmentPagerModel> mList
    </FragmentPagerModel> */
    /**
     * Sets mList.
     *
     * @param data the data
     */
    var list: List<androidx.fragment.app.Fragment> = ArrayList()
        set(items) {
            field = ArrayList(items)
            mCount = list.size
            this.notifyDataSetChanged()
        }
    private var mCount: Int = 0
    private val mCurrentFragmentList = LinkedHashMap<Int, androidx.fragment.app.Fragment>()

    init {
        mCount = list.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return androidx.viewpager.widget.PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return mCount
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as androidx.fragment.app.Fragment
        mCurrentFragmentList[position] = fragment
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        mCurrentFragmentList.remove(position)
        super.destroyItem(container, position, `object`)
    }

    open fun getFragment(position: Int): androidx.fragment.app.Fragment? {
        return if (mCurrentFragmentList.isEmpty()) null else mCurrentFragmentList[position]
    }
}
