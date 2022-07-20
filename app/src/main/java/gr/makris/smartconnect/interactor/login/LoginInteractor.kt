package gr.makris.smartconnect.interactor.login

import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.response.users.LoginUserResponse

interface LoginInteractor {

    suspend fun getUsersAsync(): DataResult<List<UserModel>, SmartConnectErrorModel>
    suspend fun loginUserAsync(loginUserRequestBody: LoginUserRequestBody): DataResult<LoginUserModel, SmartConnectErrorModel>
}