package gr.makris.smartconnect.vm.main

import android.app.Application
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.vm.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModelImpl(application: Application): BaseViewModel(application) , MainViewModel {

    private val networkProvider = SmartConnectApplication.get().networkProvider

    override fun getUsers() {
        GlobalScope.launch {
            var getUsersResponse = executeNetworkCall {
                networkProvider.getUsersAsync()
            }

            getUsersResponse.data?.let {
                Timber.d(it.users.toString())
            } ?: getUsersResponse.error.let { error ->
                Timber.d(error)
            }
        }
    }

}