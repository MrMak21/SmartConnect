package gr.makris.smartconnect.model.users

import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var userId: String = Model.INVALID_STRING,
    var firstname: String = Model.INVALID_STRING,
    var lastname: String = Model.INVALID_STRING,
    var email: String = Model.INVALID_STRING,
    var password: String = Model.INVALID_STRING,
    var enabled: Boolean = Model.INVALID_BOOLEAN,
    var provider: String? = Model.INVALID_STRING
): Model
