package com.app.donateclaim.helper.permissions

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*

/**
 * This custom class is used to manage permission checking and request permission popup for application
 */
object MarshMallowPermission {

    private const val RECORD_PERMISSION_REQUEST_CODE = 1
    private const val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 50
    private const val CAMERA_PERMISSION_REQUEST_CODE = 51
    const val CHECK_CAMERA_AND_STORAGE_PREMISSON = 53
    private const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 54
    internal const val PERMISSION_LOCATION_CODE = 55
    private const val PERMISSION_READ_PHONE_STATE = 56
    private const val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 152
    private const val PERMISSION_CALL = 154
    internal const val READ_CONTACTS = 154




    fun checkPermissionForRecord(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForExternalStorage(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun checkPermissionForReadExternalStorage(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForCamera(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionForRecord(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_PERMISSION_REQUEST_CODE
        )

    }

    fun requestPermissionForExternalStorage(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
        )
    }


    fun requestPermissionForExternalStorageGallery(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
        )

    }

    fun requestPermissionForCamera(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkCameraPermission(context: Context): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.CAMERA
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        CHECK_CAMERA_AND_STORAGE_PREMISSON
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        CHECK_CAMERA_AND_STORAGE_PREMISSON
                    )
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    fun requestLocationPermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_LOCATION_CODE
        )
    }

    fun requestLocationPermission(fragment: Fragment) {
        fragment.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_LOCATION_CODE
        )
    }


    fun checkPermissionLocations(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        return (result == PackageManager.PERMISSION_GRANTED) && (result1 == PackageManager.PERMISSION_GRANTED)
    }

    fun getLocationPermission(): String {
        return Manifest.permission.ACCESS_FINE_LOCATION
    }

    fun requestCallPermission(context: Context) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_CALL)
    }

    fun checkPhoneCallPermission(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPermissonForReadPhoneNumber(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
        return result == PackageManager.PERMISSION_GRANTED
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun requestReadPhoneStatePermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            READ_CONTACTS
        )
    }



    fun checkPermissonForPhoneState(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPhoneStatePermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            PERMISSION_READ_PHONE_STATE
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun CheckAllMarshmallowPermissionForActivityCamera(ctx: Context, activity: Activity): Boolean {
        val permissionsNeeded = ArrayList<String>()

        val permissionsList = ArrayList<String>()
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION, ctx, activity))
            permissionsNeeded.add("Location")
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE, ctx, activity))
            permissionsNeeded.add("Phone State")


        Log.i("****message", permissionsNeeded.size.toString() + " ")


        if (permissionsList.size > 0) {
            if (permissionsNeeded.size > 0) {
                // Need Rationale
                var message = "You need to grant access to " + permissionsNeeded[0]
                for (i in 1 until permissionsNeeded.size)
                    message = message + ", " + permissionsNeeded[i]

                Log.i("****message", message)
                ActivityCompat.requestPermissions(
                    activity, permissionsList.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )

                return false
            }
            /* requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);*/

        }

        return true


    }

    private fun addPermission(
        permissionsList: MutableList<String>,
        permission: String,
        ctx: Context,
        activity: Activity
    ): Boolean {
        if (ContextCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            Log.i("****message", "add$permission")
            // Check for Rationale Option
            /* if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return false;
            else*/
            return false
        }
        return true
    }


}
