package gr.makris.smartconnect.retrofit

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import gr.makris.smartconnect.extensions.network.getResponseString
import gr.makris.smartconnect.mapper.user.toUsersListModel
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.DataResultErrorModel
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.error.CommonResponse
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.response.authToken.RefreshTokenResponse
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.response.users.LoginUserResponse
import gr.makris.smartconnect.retrofit.NetworkClientFactory.getRetrofitInstance
import timber.log.Timber

class NetworkProviderImpl: NetworkProvider {

    private val smartConnectApi: ApiInterface = getRetrofitInstance()

    override suspend fun getUsersAsync(): GetUsersResponse {
        return smartConnectApi.getUsersAsync()
    }

    override suspend fun loginUserAsync(loginUserRequestBody: LoginUserRequestBody): LoginUserResponse {
        return smartConnectApi.login(loginUserRequestBody)
    }

    override suspend fun refreshAccessToken(refreshToken: String): RefreshTokenResponse {
        return smartConnectApi.refreshAccessToken(refreshToken)
    }

}