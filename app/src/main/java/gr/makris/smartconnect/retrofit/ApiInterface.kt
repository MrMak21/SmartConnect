package gr.makris.smartconnect.retrofit


import gr.makris.smartconnect.response.users.GetUsersResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/smartConnect/getUsers")
    suspend fun getUsersAsync(): GetUsersResponse
}