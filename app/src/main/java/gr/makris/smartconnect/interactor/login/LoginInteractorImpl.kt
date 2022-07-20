package gr.makris.smartconnect.interactor.login

import gr.makris.smartconnect.interactor.base.SmartConnectBaseInteractor
import gr.makris.smartconnect.mapper.user.toLoginUserModel
import gr.makris.smartconnect.mapper.user.toUsersListModel
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.response.users.LoginUserResponse
import gr.makris.smartconnect.retrofit.NetworkProvider
import timber.log.Timber

class LoginInteractorImpl(
    private val networkProvider: NetworkProvider
): SmartConnectBaseInteractor(), LoginInteractor {

    override suspend fun getUsersAsync(): DataResult<List<UserModel>, SmartConnectErrorModel> {
        return try {
            val response = networkProvider.getUsersAsync()
            DataResult(response.toUsersListModel())
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(error = getErrorModel(t))
        }
    }

    override suspend fun loginUserAsync(loginUserRequestBody: LoginUserRequestBody): DataResult<LoginUserModel, SmartConnectErrorModel> {
        return try {
            val response = networkProvider.loginUserAsync(loginUserRequestBody)
            DataResult(response.toLoginUserModel())
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(error = getErrorModel(t))
        }
    }
}