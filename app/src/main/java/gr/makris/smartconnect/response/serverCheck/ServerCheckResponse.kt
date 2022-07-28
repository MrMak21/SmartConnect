package gr.makris.smartconnect.response.serverCheck

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.response.base.SmartConnectResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class ServerCheckResponse(

    @SerializedName("serverVersion")
    val serverVersion: String,

    @SerializedName("serverName")
    val serverName: String,

    @SerializedName("serverStatus")
    val serverStatus: String

): SmartConnectResponse(), Model