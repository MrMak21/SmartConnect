package gr.makris.smartconnect.model.common

import kotlinx.android.parcel.Parcelize

@Parcelize
data class SmartConnectErrorModel(
    private val status: String? = Model.INVALID_STRING,
    private val errorCode : String = Model.INVALID_STRING,
    private val errorMessage: String = Model.INVALID_STRING,
    private val throwable: Throwable = Exception(),
): ErrorModel {

    override fun isNetworkError(): Boolean {
        return false
    }

    override fun isHttpProtocolError(): Boolean {
        return false
    }

    override fun isPermissionError(): Boolean {
        return false
    }

    override fun isUnexpected(): Boolean {
        return false
    }

    override fun getThrowable(): Throwable {
        return throwable
    }
}
