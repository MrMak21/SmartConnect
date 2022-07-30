package gr.makris.smartconnect.retrofit

import gr.makris.smartconnect.data.user.User
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.DataResultErrorModel
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.response.authToken.RefreshTokenResponse
import gr.makris.smartconnect.response.serverCheck.ServerCheckResponse
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.response.users.LoginUserResponse

interface NetworkProvider {

    suspend fun getUsersAsync(): GetUsersResponse

    suspend fun loginUserAsync(loginUserRequestBody: LoginUserRequestBody): LoginUserResponse

    suspend fun loginGoogleUserAsync(idToken: String): LoginUserResponse

    suspend fun refreshAccessToken(refreshToken: String): RefreshTokenResponse

    suspend fun serverCheck(): ServerCheckResponse

}