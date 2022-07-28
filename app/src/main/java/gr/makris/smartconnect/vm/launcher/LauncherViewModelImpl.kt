package gr.makris.smartconnect.vm.launcher

import android.app.Application
import androidx.lifecycle.MutableLiveData
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.interactor.launcher.LauncherInteractorImpl
import gr.makris.smartconnect.vm.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class LauncherViewModelImpl(
    application: Application,
) : BaseViewModel(application), LauncherViewModel {

    private val interactor = LauncherInteractorImpl(SmartConnectApplication.get().networkProvider)
    override val loadingViewLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    override fun serverCheck() {
        uiScope.launch {
            loadingViewLiveData.postValue(true)

            val serverCheckResponse = executeNetworkCall {
                interactor.serverCheck()
            }

            serverCheckResponse.data?.let {
                Timber.d(it.serverName)
            } ?: serverCheckResponse.error.let {
                Timber.d(it?.errorMessage)
            }

            loadingViewLiveData.postValue(false)
        }
    }
}