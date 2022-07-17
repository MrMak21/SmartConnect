package gr.makris.smartconnect.response.users

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.data.user.User
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.response.base.SmartConnectResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class GetUsersResponse(
    @SerializedName("usersList")
    val users: List<User>
): SmartConnectResponse(), Model