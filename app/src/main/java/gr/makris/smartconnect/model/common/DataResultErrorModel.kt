package gr.makris.smartconnect.model.common

data class DataResultErrorModel<out T, out E : ErrorModel>(
    val data: T? = null,
    val error: E? = null
)
