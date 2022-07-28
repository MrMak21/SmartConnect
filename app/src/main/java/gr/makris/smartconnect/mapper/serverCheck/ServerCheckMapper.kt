package gr.makris.smartconnect.mapper.serverCheck

import gr.makris.smartconnect.model.serverCheck.ServerCheckModel
import gr.makris.smartconnect.response.serverCheck.ServerCheckResponse

fun ServerCheckResponse.toServerCheckModel(): ServerCheckModel {
    return ServerCheckModel(
        serverName = this.serverName,
        serverVersion = this.serverVersion,
        serverStatus = this.serverStatus
    )
}