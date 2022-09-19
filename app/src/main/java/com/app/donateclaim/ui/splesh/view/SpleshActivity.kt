package com.app.donateclaim.Ui.splesh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.donateclaim.BaseActivity
import com.app.donateclaim.R
import com.app.donateclaim.Ui.main.MainActivity
import com.app.donateclaim.Ui.splesh.viewmodel.RegisterUserViewModelClass
import com.app.donateclaim.databinding.ActivitySpleshBinding
import com.app.donateclaim.helper.BaseViewModelFactory
import com.app.donateclaim.rxjava.PrefData

class SpleshActivity : BaseActivity() {

    private lateinit var binding: ActivitySpleshBinding
    var registerDeviceId: String? = null
    lateinit var registerViewModel: RegisterUserViewModelClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh)
        binding = ActivitySpleshBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(
            this,
            BaseViewModelFactory { RegisterUserViewModelClass() })[RegisterUserViewModelClass::class.java]

        initView()
//        Handler().postDelayed(Runnable {
//
//            val mainIntent = Intent(this, MainActivity::class.java)
//            this.startActivity(mainIntent)
//            this.finish()
//        }, 1000)

//
//        Log.d("deviceId", id)
    }

    private fun initView() {
        registerDeviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("deviceId", registerDeviceId.toString())
        callRegisterDeviceApi()
        setObservers()
    }

    private fun setObservers() {

        registerViewModel.registerDevicesViewModel.observe(this) { it ->
            if (it.status == "1") {
                localPref.setStringPrefs(PrefData.UserId, it.userId.toString())
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtra("userId",it.userId)
                this.startActivity(mainIntent)
                this.finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callRegisterDeviceApi() {
        registerViewModel.registerDevicesApi(deviceId = registerDeviceId!!)
    }
}