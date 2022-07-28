package gr.makris.smartconnect.interactor.launcher

import gr.makris.smartconnect.interactor.base.SmartConnectBaseInteractor
import gr.makris.smartconnect.mapper.serverCheck.toServerCheckModel
import gr.makris.smartconnect.mapper.user.toLoginUserModel
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.ErrorModel
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.serverCheck.ServerCheckModel
import gr.makris.smartconnect.retrofit.NetworkProvider
import timber.log.Timber

class LauncherInteractorImpl(
    private val networkProvider: NetworkProvider
): SmartConnectBaseInteractor(), LauncherInteractor {

    override suspend fun serverCheck(): DataResult<ServerCheckModel, SmartConnectErrorModel> {
        return try {
            val response = networkProvider.serverCheck()
            DataResult(response.toServerCheckModel())
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(error = getErrorModel(t))
        }
    }


}