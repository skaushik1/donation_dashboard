
package com.app.donateclaim.Ui.splesh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.donateclaim.Ui.main.MainActivity
import com.app.donateclaim.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh)


//
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */

            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()

        }, 1000)
    }
}