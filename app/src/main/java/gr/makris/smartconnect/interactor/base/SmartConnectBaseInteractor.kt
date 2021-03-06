package gr.makris.smartconnect.interactor.base

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import gr.makris.smartconnect.extensions.network.getResponseString
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.model.error.CommonResponse

open class SmartConnectBaseInteractor {

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