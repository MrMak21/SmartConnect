package gr.makris.smartconnect.requests.login

import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginUserRequestBody(
    val email: String,
    val password: String
): Model
