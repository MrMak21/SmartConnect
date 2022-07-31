package gr.makris.smartconnect.interactor.launcher

import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.ErrorModel
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.serverCheck.ServerCheckModel
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody

interface LauncherInteractor {

    suspend fun serverCheck(): DataResult<ServerCheckModel, SmartConnectErrorModel>
    suspend fun silentLoginUserAsync(loginUserRequestBody: LoginUserRequestBody): DataResult<LoginUserModel, SmartConnectErrorModel>

}