package com.app.donateclaim.api

import android.annotation.SuppressLint
import android.util.Base64;
import java.io.UnsupportedEncodingException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object  Security {
    private val TAG = Security::class.java.simpleName

    @SuppressLint("GetInstance")
    fun encrypt(guid: String, globalPassword: String): String? {
        var crypted: ByteArray? = null
        try {
            val skey = SecretKeySpec(globalPassword.toByteArray(), "AES")
            val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, skey)
            crypted = cipher.doFinal(guid.toByteArray())
        } catch (e: Exception) {

            println(e.toString())
            e.printStackTrace()
        }
        return Base64.encodeToString(crypted, Base64.NO_WRAP)
    }

    fun decrypt(input: String, key: String): String {

        try {
            val skeySpec = getKey(key)
            val iv = ByteArray(0)
            Arrays.fill(iv, 0x00.toByte())
            val ivParameterSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec)
            val encrypedPwdBytes = Base64.decode(input, Base64.DEFAULT)
            val decrypedValueBytes = cipher.doFinal(encrypedPwdBytes)
            return String(decrypedValueBytes)

        } catch (e: Exception) {
            println(e.toString())
            e.printStackTrace()
            return ""
        }

    }

    /**
     * Generates a SecretKeySpec for given password
     *
     * @return SecretKeySpec
     * @throws UnsupportedEncodingException
     */
    @Throws(UnsupportedEncodingException::class)
    fun getKey(masterKey: String): SecretKeySpec {

        // You can change it to 128 if you wish
        val keyLength = 256
        val keyBytes = ByteArray(keyLength / 8)
        // explicitly fill with zeros
        Arrays.fill(keyBytes, 0x0.toByte())

        // if password is shorter then key length, it will be zero-padded
        // to key length
        val passwordBytes = masterKey.toByteArray(charset("UTF-8"))
        val length = if (passwordBytes.size < keyBytes.size) passwordBytes.size else keyBytes.size
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length)
        return SecretKeySpec(keyBytes, "AES")
    }


}