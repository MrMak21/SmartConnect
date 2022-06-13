package gr.makris.smartconnect.model.common

data class DataResult<out T, out E : Throwable>(
    val data: T? = null,
    val error: E? = null
)
