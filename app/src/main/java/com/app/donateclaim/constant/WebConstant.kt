package com.app.donateclaim.constant

import com.google.gson.JsonObject


/**
 * This class contains all the api urls and also params related to it.
 */
class WebConstant {


    companion object {
        const val SERVER_BASE_URL = "http://44.228.249.93/"
        private const val API_URL = SERVER_BASE_URL + "Donate/WS/Service.php?Service="

        const val PROFILE_PIC_LOAD_URL =
            "http://clientapp.narola.online/pg/taxibooking/api/Uploads/Users/" //todo change with live url

        const val TERMS_CONDITION_URL =
            "https://www.clickdimensions.com/links/TestPDFfile.pdf" //todo change with live url
        const val PRIVACY_POLICY_URL =
            "https://www.clickdimensions.com/links/TestPDFfile.pdf" //todo change with live url


        const val VEHICLE_TYPE_LOAD_URL =
            "http://clientapp.narola.online/pg/taxibooking/api/Uploads/VehicleType/" //todo change with live url


        const val PACKAGE_PHOTO_URl =
            "http://clientapp.narola.online/pg/taxibooking/api/Uploads/PackageDelivery/" //todo change with live url


        //API
        const val Register = API_URL + "Register"
        const val GetProductList = API_URL + "GetProductList"
        const val ProductDetails = API_URL + "ProductDetails"

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
            type:String,
        ): JsonObject {
            val jsonObject = JsonObject()
            return jsonObject
        }



    }
}




