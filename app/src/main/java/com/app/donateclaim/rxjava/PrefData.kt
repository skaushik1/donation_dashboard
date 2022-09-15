package com.app.donateclaim.rxjava

import android.content.Context
import com.google.gson.Gson

/**
 * This class is used to handle any shared preference saved in app
 */
open class PrefData(context: Context) {

    private val Preference = "DonateClaim"

    private val sharedPrefs = context.getSharedPreferences(Preference, Context.MODE_PRIVATE)


    companion object {

        const val IsLoggedIn = "IsLoggedIn"
        const val IsEmailVerified = "IsEmailVerified"
        const val IsTutorialDisplayed = "IsTutorialDisplayed"
        const val UserId = "UserId"
        const val RememberMe = "RememberMe"
        const val isPhoneVerified = "isPhoneVerified"
        const val UserEmail = "UserEmail"
        const val UserPassword = "UserPassword"
        const val FcmToken = "FcmToken"
        const val UserProfilePic = "ProfilePic"
        const val UserName = "userName"
        const val userToken = "UserToken"
        const val deviceToken = "DeviceToken"
        const val deviceType = "DeviceType"
        const val SocialId = "socialId"
        const val socialMediaProfilePic = "socialMediaProfilePic"
        const val guid = "guid"
        const val globalPassword = "globalPassword"
        const val accessKey = "accessKey"
        const val latitude = "latitude"
        const val longitude = "longitude"
        const val endDestinaction = "endDestinaction"
        const val startDestinaction = "startDestinaction"
        const val endDestinationLatitude = "endDestinationLatitude"
        const val endDestinationLongitude = "endDestinationLongitude"
        const val serviceTypeId="serviceTypeId"


    }

    fun setObjectPreference(prefKey: String, `object`: Any): Boolean {
        return setStringPrefs(prefKey, Gson().toJson(`object`))
    }

    fun getObjectPreference(prefKey: String, classObject: Class<*>): Any {
        return Gson().fromJson(getStringPrefs(prefKey), classObject)
    }

    fun setBooleanPrefs(prefKey: String, value: Boolean): Boolean {
        return sharedPrefs.edit().putBoolean(prefKey, value).commit()
    }

    fun getBooleanPrefs(prefKey: String): Boolean {
        return sharedPrefs.getBoolean(prefKey, false)
    }

    fun setStringPrefs(prefKey: String, Value: String): Boolean {
        return sharedPrefs.edit().putString(prefKey, Value).commit()
    }

    fun getStringPrefs(prefKey: String): String? {
        return sharedPrefs.getString(prefKey, "")
    }

    fun setIntPrefs(prefKey: String, value: Int): Boolean {
        return sharedPrefs.edit().putInt(prefKey, value).commit()
    }

    fun getIntPrefs(prefKey: String): Int {
        return sharedPrefs.getInt(prefKey, 0)
    }

    /**
     * Clear all data in SharedPreference
     */
    fun clearWholePreference(): Boolean {
        return sharedPrefs.edit().clear().commit()
    }

    /**
     * Clear single key value
     * @param prefKey
     */
    fun remove(prefKey: String): Boolean {
        return sharedPrefs.edit().remove(prefKey).commit()
    }


}