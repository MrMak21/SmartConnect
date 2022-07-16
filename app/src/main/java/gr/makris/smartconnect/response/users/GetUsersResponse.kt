package gr.makris.smartconnect.response.users

import gr.makris.smartconnect.data.user.User
import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
class GetUsersResponse(
    val users: List<User>
): Model