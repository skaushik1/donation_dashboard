package com.app.donateclaim.model

data class BaseResponse(
    var status: String = "",
    var message: String = "",
)

data class ResponseDisplay(
    var TakeAction: Boolean = true,
    var Status: Boolean = true,
    var message: String = "",
    var userid: String = "",
)


data class MessageResponse(
    var status: String = "",
    var message: String = ""
)











