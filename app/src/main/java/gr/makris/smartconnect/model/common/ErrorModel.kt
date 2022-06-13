package gr.makris.smartconnect.model.common

interface ErrorModel : Model {
    fun isNetworkError(): Boolean

    fun isHttpProtocolError(): Boolean

    fun isPermissionError(): Boolean

    fun isUnexpected(): Boolean

    fun getThrowable(): Throwable
}