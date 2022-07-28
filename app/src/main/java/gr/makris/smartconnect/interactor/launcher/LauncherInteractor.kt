package gr.makris.smartconnect.interactor.launcher

import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.ErrorModel
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.serverCheck.ServerCheckModel

interface LauncherInteractor {

    suspend fun serverCheck(): DataResult<ServerCheckModel, SmartConnectErrorModel>
}