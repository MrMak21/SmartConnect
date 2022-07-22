package gr.makris.smartconnect.model.common

data class DataResultThrowable<out T, out E : Throwable>(
    val data: T? = null,
    val error: E? = null
)
