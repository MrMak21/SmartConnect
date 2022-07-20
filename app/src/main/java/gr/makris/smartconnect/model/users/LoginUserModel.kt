package gr.makris.smartconnect.model.users

import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginUserModel(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String
): Model
