package com.app.donateclaim.helper

import android.widget.EditText
import androidx.core.text.isDigitsOnly
import com.app.donateclaim.R
import java.util.regex.Pattern

object InputValidations {

    private val EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{3,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{2,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )

    private val PASSWORD = Pattern.compile("[a-zA-Z0-9?=.*@#\$%^&+-_!]{6,20}")
    private val PHONE = Pattern.compile("^[0-9]{8,13}$")

    // For setting error message using custom string on any EditText.
    private fun setError(tvText: EditText, errorMessage: String) {
        tvText.error = errorMessage
        tvText.requestFocus()
    }

    /**
     * This method is used to check for valid text value
     */
    private fun isValidInput(text: String): Boolean {
        return text.length > 0
    }

    /**
     * Email Validation
     * @param editText - editable view object
     * @return boolean
     */
//    fun isValidEmail(editText: EditText): Boolean {
//        val text = editText.text.toString()
//        if (isValidInput(text)) {
//            if (EMAIL_ADDRESS.matcher(editText.text).matches()
//            ) {
//                setError(editText, editText.context.getString(R.string.msg_invalid_email))
//                return false
//            } else {
//                editText.error = null
//                return true
//            }
//        } else {
//            setError(editText, editText.context.getString(R.string.msg_invalid_email_empty))
//            return false
//        }
//    }










    /**
     * This method is used to validate user name
     */
    fun isValidUserName(editText: EditText): Boolean {
        val text = editText.text.toString()
        if (text.length > 4 && text.length < 11) {
            setError(editText, editText.context.getString(R.string.msg_invalid_user_name))
            return false
        }
        return isValidInput(text)
    }
    /**
     * This method is used to validate user name
     */
    fun isValidLastName(editText: EditText): Boolean {
        val text = editText.text.toString()
        if (text.length < 3) {
            setError(editText, editText.context.getString(R.string.msg_invalid_last_name))
            return false
        }
        return isValidInput(text)
    }
    /**
     * This method is used to Gender
     */
    fun isGender(editText: EditText): Boolean {
        val text = editText.text.toString()
        if (text.length < 3) {
            setError(editText, editText.context.getString(R.string.please_Enter_Gender))
            return false
        }
        return isValidInput(text)
    }

    /**
     * This method is used to validate user name
     */
    fun isValidOtpCode(editText: EditText): Boolean {
        val text = editText.text.toString()
        if (text.isEmpty()) {
            setError(editText, editText.context.getString(R.string.msg_invalid_otp))
            return false
        } else {
            if (text.isDigitsOnly().not()) {
                setError(editText, editText.context.getString(R.string.msg_invalid_otp))
                return false
            }
        }
        return isValidInput(text)
    }





}
