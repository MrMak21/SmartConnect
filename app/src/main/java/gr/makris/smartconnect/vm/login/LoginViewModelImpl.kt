package gr.makris.smartconnect.vm.login

import android.app.Application
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.interactor.login.LoginInteractor
import gr.makris.smartconnect.interactor.login.LoginInteractorImpl
import gr.makris.smartconnect.requests.login.LoginUserRequestBody

import gr.makris.smartconnect.vm.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModelImpl(
    application: Application,
) : BaseViewModel(application), LoginViewModel {

    private val interactor: LoginInteractor = LoginInteractorImpl(SmartConnectApplication.get().networkProvider)

    override fun getUsersAsync() {
        GlobalScope.launch {

            val loginUserRequestsBody = LoginUserRequestBody("panosmak37@gmail.com", "123456")

            val response = executeNetworkCall {
                interactor.loginUserAsync(loginUserRequestsBody)
            }

            response.data?.let {
                Timber.d(it.accessToken)
            } ?: response.error.let {
                Timber.d(it?.errorMessage)
            }
        }
    }


}