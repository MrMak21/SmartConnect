package gr.makris.smartconnect.data.user

import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var userId: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var enabled: Boolean,
    var provider: String
): Model
