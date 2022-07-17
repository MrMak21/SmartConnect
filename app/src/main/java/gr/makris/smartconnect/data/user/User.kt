package gr.makris.smartconnect.data.user

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("userId")
    var userId: String,

    @SerializedName("firstname")
    var firstname: String,

    @SerializedName("lastname")
    var lastname: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("enabled")
    var enabled: Boolean,

    @SerializedName("provider")
    var provider: String?
): Model
