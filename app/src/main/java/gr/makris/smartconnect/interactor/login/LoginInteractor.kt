package gr.makris.smartconnect.interactor.login

import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.users.UserModel

interface LoginInteractor {

    suspend fun getUsersAsync(): DataResult<List<UserModel>, SmartConnectErrorModel>
}