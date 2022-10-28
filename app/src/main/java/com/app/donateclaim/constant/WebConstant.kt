package com.app.donateclaim.constant

import com.google.gson.JsonObject


/**
 * This class contains all the api urls and also params related to it.
 */
class WebConstant {


    companion object {
        const val SERVER_BASE_URL = "http://44.228.249.93/"
        private const val API_URL = SERVER_BASE_URL + "Donate/WS/Service.php?Service="



        //API
        const val Register = API_URL + "Register"
        const val GetProductList = API_URL + "GetProductList"
        const val ProductDetails = API_URL + "ProductDetails"
        const val claimProduct = API_URL + "ClaimProduct"
        const val addProducts = API_URL + "AddProducts"
        const val disableProduct = API_URL + "DisablePost"

    }


    enum class Status(val status: Int) {
        Success(1), Failed(0), MaliciousSource(2);
    }

    /**
     * This key are used for passing in header
     */


    class HeaderKey {


        companion object {

            const val KeyUserAgent = "User-Agent"
            const val KeyContentType = "Content-Type"
            const val KeySecretKey = "secretKey"
            const val IsTestData = "IsTestdata"
            const val KeyaccessKey = "accessKey"


            const val ValueContentType: String = "application/json"
            const val ValueUserAgent: String = "Android"
            var ValueSecretKeyDefault: String =
                "" // update with SecretKey get from API
            const val ValueIsTestData: String = "1"
            var ValueaccessKey: String = "nousername"
        }
    }

    /**
     * This key are used for passing in request as parameter
     */
    class RequestParams {
        companion object {
            const val devicesId = "device_id"
            const val userId = "user_id"
            const val type = "type"
            const val id = "id"
            const val product_id = "product_id"
            const val name = "name"
            const val EmailId = "email_id"
            const val PhoneNumber = "phone_number"
            const val is_disable = "is_disable"

        }
    }

    /************************************************
     * Json request params section
     ************************************************/

    object ApiRequestData {
        /**
         * This method is used to create request json body for login api
         */
        fun registerDevicesIdRequestBody(
            devicesId: String,
            ): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty(RequestParams.devicesId, devicesId)
            return jsonObject
        }


        /**
         * This method is used to create post
         */
        fun getAllProduct(
            user_id:String,
            type:String
        ): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty(RequestParams.userId, user_id)
            jsonObject.addProperty(RequestParams.type, type)
            return jsonObject
        }


        /**
         * This method is used to GetProduct Details
         */
        fun getAllProductDetails(
            id: String,
            type:Int,
        ): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty(RequestParams.id, id)
            jsonObject.addProperty(RequestParams.type, type)
            return jsonObject
        }


        /**
         * This method is used to DisebalPost
         */
        fun disablePost(
            id: String,
            is_disable:String,
        ): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty(RequestParams.id, id)
            jsonObject.addProperty(RequestParams.devicesId, is_disable)
            return jsonObject
        }



        /**
         * This method is used to Claim Product
         */
        fun claimProductRequestBody(
            user_id: String,
            product_id:String,
            name:String,
            email_id:String,
            phone_number:String,
        ): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty(RequestParams.userId, user_id)
            jsonObject.addProperty(RequestParams.product_id, product_id)
            jsonObject.addProperty(RequestParams.name, name)
            jsonObject.addProperty(RequestParams.EmailId, email_id)
            jsonObject.addProperty(RequestParams.PhoneNumber, phone_number)

            return jsonObject
        }
    }
}




