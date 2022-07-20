package gr.makris.smartconnect.response.users

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.data.user.User
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.response.base.SmartConnectResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginUserResponse(
    @SerializedName("user")
    val user: User,

    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String
): SmartConnectResponse(), Model
