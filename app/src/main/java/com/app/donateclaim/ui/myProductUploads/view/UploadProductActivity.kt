package com.app.donateclaim.Ui.myProductUploads.view


import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.donateclaim.base.BaseActivity
import com.app.donateclaim.R
import com.app.donateclaim.Ui.myProductUploads.adapter.ProductImageAdapterclass
import com.app.donateclaim.Ui.myProductUploads.viewmodel.ProductUploadViewModel
import com.app.donateclaim.databinding.ActivityUploadPostBinding
import com.app.donateclaim.databinding.DailogChooseImageBinding
import com.app.donateclaim.base.BaseViewModelFactory
import com.app.donateclaim.helper.permissions.MarshMallowPermission
import com.app.donateclaim.helper.permissions.getWidth
import com.app.donateclaim.helper.PrefData
import java.io.*
import java.util.*


class UploadProductActivity : BaseActivity() {
    private lateinit var binding: ActivityUploadPostBinding
    lateinit var productImageAdapterclass: ProductImageAdapterclass
    var productImage: MutableList<String> = mutableListOf()
    lateinit var packageItemUrl: String
    private val emailPattern = Patterns.EMAIL_ADDRESS
    lateinit var productUploadViewModel: ProductUploadViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)

        binding = ActivityUploadPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        initview()


    }

    private fun setOnClick() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            uploadProductApi()
        }
    }

    private fun uploadProductApi() {
        if (isValidData(
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                quantity = binding.etQuantity.text.toString(),
                name = binding.etFullName.text.toString(),
                email = binding.etEmail.text.toString(),
                phonenumber = binding.etPhoneNo.text.toString()

            )
        ) {

            if (productImage.isEmpty()){
                Toast.makeText(this, "Please Select Your image", Toast.LENGTH_SHORT).show()
            }

            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val quantity = binding.etQuantity.text.toString()
            val name = binding.etFullName.text.toString()
            val email=binding.etEmail.text.toString()
            val phone=binding.etPhoneNo.text.toString()



            productUploadViewModel.callCreateProductApi(
                userId = localPref.getStringPrefs(PrefData.UserId)!!,
                title, description, quantity,name,email,phone,productImage
            )
        }





}

private fun isValidData(title: String, description: String, quantity: String,name:String,email:String,phonenumber:String): Boolean {

    val validQuantity: Boolean
    val validTitle: Boolean
    val validDescription: Boolean
    val validUserName: Boolean
    var validEmail: Boolean
    val validPhoneNo: Boolean


    if (title.isEmpty()) {
        binding.etTitle.error = "Enter Your product Title"
        validTitle = false
    } else {
        validTitle = true
    }

    if (description.isEmpty()) {
        binding.etDescription.error = "Enter Your description"
        validDescription = false
    } else {
        validDescription = true
    }

    if (quantity.isEmpty()) {
        binding.etQuantity.error = "Enter Your description"
        validQuantity = false
    } else {
        validQuantity = true
    }



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


//    if (email.isEmpty()) {
//
//        validEmail = false
//    } else {
//        validEmail = true
//    }

    if (phonenumber.isEmpty()) {
        binding.etPhoneNo.error = "Enter Your PhoneNo"
        validPhoneNo = false
    } else {
        validPhoneNo = true
    }


    if (validTitle) {
        binding.etTitle.error = null
    }

    if (validDescription) {
        binding.etDescription.error = null
    }
    if (validQuantity) {
        binding.etQuantity.error = null
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

    return validTitle && validDescription && validQuantity && validUserName && validEmail && validPhoneNo
}

private fun showChooseImageOptions() {
    val dialog = Dialog(this)
    val dialogChooseImageBinding = DailogChooseImageBinding
        .inflate(LayoutInflater.from(this), binding.root as ViewGroup, false)
    dialog.setContentView(dialogChooseImageBinding.root)
    dialog.setCanceledOnTouchOutside(true)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val width = (getWidth(this) * 0.9f).toInt()
    val window = dialog.window
    val wlp: WindowManager.LayoutParams = window?.attributes!!
    wlp.gravity = Gravity.CENTER
    wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
    window.attributes = wlp

    dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    dialogChooseImageBinding.ivCamera.setOnClickListener {
        openCamera()
        dialog.dismiss()

    }

    dialogChooseImageBinding.ivGallery.setOnClickListener {
        openGallery()
        dialog.dismiss()
    }


//        dialogChooseImageBinding.closeImageview.throttleClicks().subscribe {
//            dialog.dismiss()
//        }.autoDispose()

    dialog.show()
}

private fun openGallery() {
    val intent = Intent()
    intent.type = "image/*"
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(
        Intent.createChooser(intent, "Select Picture"),
        101
    )

}

private fun openCamera() {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        takePictureIntent.resolveActivity(this.packageManager).also {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.app.donateclaim.fileprovider",
                    it
                )


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(
                    takePictureIntent,
                    100
                )

            }
        }
    }
}

@Throws(IOException::class)
private fun createImageFile(): File {
    // Create an image file name
    val timeStamp: String = java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        packageItemUrl = absolutePath
    }
}


@RequiresApi(Build.VERSION_CODES.KITKAT)
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        MarshMallowPermission.CHECK_CAMERA_AND_STORAGE_PREMISSON -> {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                showChooseImageOptions()
            } else {
                MarshMallowPermission.requestPermissionForCamera(this)
            }
            return
        }
    }
}

private fun initview() {


    productUploadViewModel = ViewModelProvider(
        this,
        BaseViewModelFactory { ProductUploadViewModel() })[ProductUploadViewModel::class.java]



    productImageAdapterclass = ProductImageAdapterclass(this).apply {
        itemClick = { index, model ->
            if (MarshMallowPermission.checkCameraPermission(this@UploadProductActivity)) {
                showChooseImageOptions()
                Log.d("xyz", binding.ivChooseImages.toString())
            } else {
                MarshMallowPermission.requestPermissionForCamera(this@UploadProductActivity)
            }
        }

    }
    binding.rvProductImage.adapter = productImageAdapterclass
    val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)
    binding.rvProductImage.setLayoutManager(layoutManager)
    productImageAdapterclass.setData(mutableListOf())


    setObserver()
}

    private fun setObserver() {
        productUploadViewModel.createProductResponse.observe(this) { it ->
            if (it.status == "1") {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        productUploadViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                this.showProgress(this)
            } else {
                this.hideProgress()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 100) {
        if (resultCode == RESULT_OK) {
            compressimage(packageItemUrl)
            productImage.addAll(mutableListOf(packageItemUrl))
            productImageAdapterclass.setData(productImage)
        } else {
            // Result was a failure
            Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
        }

    } else if (requestCode == 101 && resultCode == Activity.RESULT_OK
        && null != data
    ) {
        if (data.clipData != null) {
            val count = data.clipData!!.itemCount
            for (i in 0 until count) {
                val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                getPathFromURI(imageUri)
            }
        } else if (data.data != null) {
            packageItemUrl = data.data?.path.toString()
            productImage.addAll(mutableListOf(packageItemUrl))
        }

        if (productImage.size == 5) {
            binding.ivChooseImages.visibility = View.GONE
        }
        productImageAdapterclass?.setData(productImage)


//            if (feedbackImage.size == 5) {
//                binding.chooesImagesImageView.visibility = View.GONE
//            }

    }

}




    fun compressimage(filePath: String?) {
        val compressionRatio = 2 //1 == originalImage, 2 = 50% compression, 4=25% compress
        val file = File(filePath.toString())
        try {
            var bitmap = BitmapFactory.decodeFile(file.path)
            bitmap.compress(CompressFormat.JPEG, compressionRatio, FileOutputStream(file))
            //bitmap = Bitmap.createScaledBitmap(bitmap, 160, 160, true);
        } catch (t: Throwable) {
            Log.e("ERROR", "Error compressing file.$t")
            t.printStackTrace()
        }
    }





    private fun getPathFromURI(imageUri: Uri) {
    val path: String = imageUri.path!! // uri = any content Uri

    val databaseUri: Uri
    val selection: String?
    val selectionArgs: Array<String>?
    if (path.contains("/document/image:")) { // files selected from "Documents"
        databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        selection = "_id=?"
        selectionArgs = arrayOf(DocumentsContract.getDocumentId(imageUri).split(":")[1])
    } else {
        // files selected from all other sources, especially on Samsung devices
        databaseUri = imageUri
        selection = null
        selectionArgs = null
    }
    try {
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.ORIENTATION,
            MediaStore.Images.Media.DATE_TAKEN
        ) // some example data you can query
        val cursor = this.contentResolver.query(
            databaseUri,
            projection, selection, selectionArgs, null
        )
        if (cursor!!.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(projection[0])
            packageItemUrl = cursor.getString(columnIndex)
            Log.e("path", packageItemUrl);
            productImage.add(packageItemUrl)
            compressimage(packageItemUrl)
            Log.d("totalsize", productImage.size.toString())
            //compressimage(packageItemUrl)
        }
        cursor.close()
    } catch (e: Exception) {
        Log.e(ContentValues.TAG, e.message, e)
    }

}
}