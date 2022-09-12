package com.app.donateclaim.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.donateclaim.BaseActivity
import com.app.donateclaim.MainActivity
import com.app.donateclaim.R
import com.app.donateclaim.databinding.ActivitySpleshBinding

class SpleshActivity : BaseActivity() {

    private lateinit var binding: ActivitySpleshBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splesh)
        binding = ActivitySpleshBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()



//
//        Handler(Looper.getMainLooper()).postDelayed({
//            /* Create an Intent that will start the Menu-Activity. */
//
//
//
//        }, 2000)
    }
}