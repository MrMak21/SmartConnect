package gr.makris.smartconnect.vm.base

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.DataResultErrorModel
import gr.makris.smartconnect.model.common.ErrorModel
import kotlinx.coroutines.*

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    var uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    var bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    val bgDispatcherNonPausable: CoroutineDispatcher = Dispatchers.IO

    protected var job = SupervisorJob()
    protected var uiScope = CoroutineScope(Dispatchers.Main + job)

    @UiThread
    suspend fun <T, E : ErrorModel> executeNetworkCall(
        handleLoading: Boolean = false,
        isPausable: Boolean = true,
        networkCall: suspend () -> DataResult<T, E>
    ): DataResult<T, E> {


        val bgDispatcher = if (isPausable) bgDispatcher else bgDispatcherNonPausable
        return withContext(bgDispatcher) { networkCall() }
    }

}