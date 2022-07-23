package gr.makris.smartconnect.retrofit

import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.response.authToken.RefreshTokenResponse
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.response.users.LoginUserResponse
import gr.makris.smartconnect.retrofit.NetworkClientFactory.getSmartConnectApi
import gr.makris.smartconnect.retrofit.NetworkClientFactory.getSmartConnectAuthenticatedApi

class NetworkProviderImpl: NetworkProvider {

    private val smartConnectApi: SmartConnectApi = getSmartConnectAuthenticatedApi()
    private val smartConnectNonAuthenticatedApi: SmartConnectApi = getSmartConnectApi()

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