package gr.makris.smartconnect.vm.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.interactor.login.LoginInteractor
import gr.makris.smartconnect.interactor.login.LoginInteractorImpl
import gr.makris.smartconnect.model.Definitions
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody

import gr.makris.smartconnect.vm.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModelImpl(
    application: Application,
) : BaseViewModel(application), LoginViewModel {

    private val interactor: LoginInteractor =
        LoginInteractorImpl(SmartConnectApplication.get().networkProvider)
    private val sharedPrefsProvider = SmartConnectApplication.get().sharedPreferencesProvider

    override val loadingViewLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val loginUserLiveData: MutableLiveData<LoginUserModel> by lazy { MutableLiveData<LoginUserModel>() }
    override val errorLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }


    override fun loginUser(email: String, password: String) {
        uiScope.launch {
            loadingViewLiveData.postValue(true)
            val loginUserRequestsBody = LoginUserRequestBody(email, password)

            val response = executeNetworkCall {
                interactor.loginUserAsync(loginUserRequestsBody)
            }

            response.data?.let {
                Timber.d(it.accessToken)
                sharedPrefsProvider.putString(Definitions.ACCESS_TOKEN_PREFERENCES, it.accessToken)
                sharedPrefsProvider.putString(Definitions.REFRESH_TOKEN_PREFERENCES, it.refreshToken)
                loginUserLiveData.postValue(it)
                loadingViewLiveData.postValue(false)
            } ?: response.error.let {
                Timber.d(it?.errorMessage)
                loadingViewLiveData.postValue(false)
                errorLiveData.postValue(it?.errorMessage)
            }
        }
    }

    override fun getUsersAsync() {
        uiScope.launch {
            loadingViewLiveData.postValue(true)
            val response = executeNetworkCall {
                interactor.getUsersAsync()
            }

            response.data?.let {
                Timber.d(it.toString())
            } ?: response.error.let {
                Timber.d(it?.errorMessage)
            }

            loadingViewLiveData.postValue(false)
        }
    }

    override fun loginGoogleUser(idToken: String) {
        uiScope.launch {
            loadingViewLiveData.postValue(true)

            val googleLoginResponse = executeNetworkCall {
                interactor.loginGoogleUser(idToken)
            }

            googleLoginResponse.data?.let {
                sharedPrefsProvider.putString(Definitions.ACCESS_TOKEN_PREFERENCES, it.accessToken)
                sharedPrefsProvider.putString(Definitions.REFRESH_TOKEN_PREFERENCES, it.refreshToken)
                loginUserLiveData.postValue(it)
            } ?: googleLoginResponse.error?.let {
                Timber.d(it.errorMessage)
                errorLiveData.postValue(it.errorMessage)
            }
            loadingViewLiveData.postValue(false)
        }
    }


}