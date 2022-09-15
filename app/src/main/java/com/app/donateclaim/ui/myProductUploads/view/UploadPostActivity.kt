package com.app.donateclaim.Ui.myProductUploads.view

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.donateclaim.R
import com.app.donateclaim.Ui.myProductUploads.adapter.ProductImageAdapterclass
import com.app.donateclaim.databinding.ActivityUploadPostBinding
import com.app.donateclaim.databinding.DailogChooseImageBinding
import com.app.donateclaim.helper.permissions.MarshMallowPermission
import com.app.donateclaim.helper.permissions.getWidth
import java.io.File
import java.io.IOException
import java.util.*

class UploadPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadPostBinding
    lateinit var productImageAdapterclass: ProductImageAdapterclass
    var productImage: MutableList<String> = mutableListOf()
    lateinit var packageItemUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)
        binding = ActivityUploadPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        initview()

    }

    private fun setOnClick() {
//        binding.ivChooseImages.setOnClickListener {
//            if (MarshMallowPermission.checkCameraPermission(this)) {
//                showChooseImageOptions()
//                Log.d("xyz", binding.ivChooseImages.toString())
//            } else {
//                MarshMallowPermission.requestPermissionForCamera(this)
//            }
//        }



        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
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

        dialogChooseImageBinding.btnCamera.setOnClickListener {
            openCamera()
            dialog.dismiss()
        }

        dialogChooseImageBinding.btnGallery.setOnClickListener {
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
        productImageAdapterclass = ProductImageAdapterclass(this).apply {
            itemClick = { index, model ->
                if (MarshMallowPermission.checkCameraPermission(this@UploadPostActivity)) {
                    showChooseImageOptions()
                    Log.d("xyz", binding.ivChooseImages.toString())
                } else {
                    MarshMallowPermission.requestPermissionForCamera(this@UploadPostActivity)
                }
            }

        }
        binding.rvProductImage.adapter = productImageAdapterclass
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)
        binding.rvProductImage.setLayoutManager(layoutManager)
        productImageAdapterclass.setData(mutableListOf())
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                productImage.addAll(mutableListOf(packageItemUrl))
                productImageAdapterclass.setData(productImage)

            } else {
                // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }

        } else if (requestCode ==101 && resultCode == Activity.RESULT_OK
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
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message, e)
        }

    }
}