package gr.makris.smartconnect.retrofit


import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.response.authToken.RefreshTokenResponse
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.response.users.LoginUserResponse
import retrofit2.http.*

interface ApiInterface {

    @GET("api/smartConnect/getUsers")
    suspend fun getUsersAsync(): GetUsersResponse

    @POST("api/smartConnect/loginUser")
    suspend fun login(@Body loginUserRequestBody: LoginUserRequestBody): LoginUserResponse

    @GET("api/smartConnect/refreshAccessToken")
    suspend fun refreshAccessToken(@Query("refreshToken") refreshToken: String): RefreshTokenResponse
}