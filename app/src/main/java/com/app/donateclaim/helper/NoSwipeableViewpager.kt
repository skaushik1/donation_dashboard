package com.app.donateclaim.helper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import java.lang.Exception

class NoSwipeableViewpager: ViewPager {
    constructor(context: Context): super(context){
        setmyscroller()
    }

    constructor(context: Context,attributeSet: AttributeSet): super(context,attributeSet){
        setmyscroller()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    private fun setmyscroller() {
        try {
            val viewPager = ViewPager::class.java
            val scroller = viewPager.getDeclaredField("mscroller")
            scroller.isAccessible=true
            scroller.set(this,Myscroller(context))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    inner class Myscroller(context: Context?): Scroller(context,DecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 400)
        }
    }
}