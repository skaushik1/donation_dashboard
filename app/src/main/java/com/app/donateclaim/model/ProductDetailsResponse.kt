package com.app.donateclaim.model

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(

	@field:SerializedName("Claimed_Product")
	val claimedProduct: List<ClaimedProductItem> = listOf(),

	@field:SerializedName("Media")
	val media: List<MediaItem> = listOf(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ClaimedProductItem(

	@field:SerializedName("email_id")
	val emailId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null
)

data class MediaItem(

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("media_name")
	val mediaName: String? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("modified_date")
	val modifiedDate: String? = null,

	@field:SerializedName("is_testdata")
	val isTestdata: Int? = null,

	@field:SerializedName("is_delete")
	val isDelete: Int? = null
)
