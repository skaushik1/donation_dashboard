package com.app.donateclaim.model

import com.google.gson.annotations.SerializedName

data class RegisterDevicesResponse(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
