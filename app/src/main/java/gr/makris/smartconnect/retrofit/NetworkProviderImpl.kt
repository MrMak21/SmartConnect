package gr.makris.smartconnect.retrofit

import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.retrofit.NetworkClientFactory.getRetrofitInstance
import timber.log.Timber

class NetworkProviderImpl: NetworkProvider {

    private val smartConnectApi: ApiInterface = getRetrofitInstance()

    override suspend fun getUsersAsync(): DataResult<GetUsersResponse, Throwable> {
        return try {
            val response = smartConnectApi.getUsersAsync()
            DataResult(response)
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(error = t)
        }

    }


}