package gr.makris.smartconnect.vm.base

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import gr.makris.smartconnect.model.common.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    var uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    var bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    val bgDispatcherNonPausable: CoroutineDispatcher = Dispatchers.IO

    @UiThread
    suspend fun <T, E : Throwable> executeNetworkCall(
        handleLoading: Boolean = false,
        isPausable: Boolean = true,
        networkCall: suspend () -> DataResult<T, E>
    ): DataResult<T, E> {


        val bgDispatcher = if (isPausable) bgDispatcher else bgDispatcherNonPausable
        return withContext(bgDispatcher) { networkCall() }
    }
}