package com.app.donateclaim.model

import com.google.gson.annotations.SerializedName

data class GetProductResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("Products")
	val products: List<ProductsItem> = listOf()
)

data class ProductsItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("media_name")
	val mediaName: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
