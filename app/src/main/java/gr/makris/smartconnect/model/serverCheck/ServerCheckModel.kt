package gr.makris.smartconnect.model.serverCheck

import gr.makris.smartconnect.model.common.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerCheckModel(
    var serverName: String = Model.INVALID_STRING,
    var serverVersion: String = Model.INVALID_STRING,
    var serverStatus: String = Model.INVALID_STRING
): Model
