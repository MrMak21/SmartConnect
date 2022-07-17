package gr.makris.smartconnect.retrofit

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import gr.makris.smartconnect.extensions.network.getResponseString
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.DataResultErrorModel
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.error.CommonResponse
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.retrofit.NetworkClientFactory.getRetrofitInstance
import timber.log.Timber

class NetworkProviderImpl: NetworkProvider {

    private val smartConnectApi: ApiInterface = getRetrofitInstance()

    override suspend fun getUsersAsync(): DataResult<GetUsersResponse, SmartConnectErrorModel> {
        return try {
            val response = smartConnectApi.getUsersAsync()
            DataResult(response)
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(error = getErrorModel(t))
        }
    }

    fun getErrorModel(throwable: Throwable): SmartConnectErrorModel {
        return SmartConnectErrorModel(
            errorCode = getCommonResponse(throwable)?.let {
                getErrorCode(it) ?: Model.INVALID_STRING
            } ?: run { Model.INVALID_STRING },
            errorMessage = getCommonResponse(throwable)?.let {
                getErrorMessage(it) ?: Model.INVALID_STRING
            } ?: run { Model.INVALID_STRING },
            status = getCommonResponse(throwable)?.let {
                getHttpCode(it) ?: Model.INVALID_STRING
            } ?: run { Model.INVALID_STRING }
        )
    }

    protected fun getCommonResponse(throwable: Throwable): CommonResponse? {
        return throwable.getAsResponse(CommonResponse::class.java)
    }

    fun <T> Throwable?.getAsResponse(tClass: Class<T>): T? {
        val responseString = getResponseString() ?: return null
        //This is needed because when the access token expires the server
        //will send a response who's body is in XML format, which would crash here
        return try {
            Gson().fromJson(responseString, tClass)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    protected fun getErrorMessage(commonResponse: CommonResponse): String? {
        if (!commonResponse.errors.isNullOrEmpty()) {
            return commonResponse.errors[0].description
        }
        return null
    }

    protected fun getErrorCode(commonResponse: CommonResponse): String? {
        if (!commonResponse.errors.isNullOrEmpty()) {
            return commonResponse.errors[0].code
        }
        return null
    }

    protected fun getHttpCode(commonResponse: CommonResponse): String? {
        if (!commonResponse.status.isNullOrEmpty()) {
            return commonResponse.status
        }
        return null
    }


}