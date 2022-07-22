package gr.makris.smartconnect.response.base

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.model.common.Model

open class SmartConnectResponse(
    @SerializedName("timestamp")
    val timestamp: String = Model.INVALID_STRING,

    @SerializedName("errors")
    val errors: List<CustomError> = arrayListOf()
)

class CustomError(
    @SerializedName("code")
    val code: String? = Model.INVALID_STRING,

    @SerializedName("description")
    val description: String? = Model.INVALID_STRING
)
