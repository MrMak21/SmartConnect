package gr.makris.smartconnect.response.authToken

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.response.base.SmartConnectResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefreshTokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String
): SmartConnectResponse(), Model