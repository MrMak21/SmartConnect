package gr.makris.smartconnect.vm.launcher

import android.app.Application
import androidx.lifecycle.MutableLiveData
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.interactor.launcher.LauncherInteractorImpl
import gr.makris.smartconnect.model.Definitions.LOGIN_USER_EMAIL
import gr.makris.smartconnect.model.Definitions.LOGIN_USER_PASSWORD
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.requests.login.LoginUserRequestBody
import gr.makris.smartconnect.vm.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class LauncherViewModelImpl(
    application: Application,
) : BaseViewModel(application), LauncherViewModel {

    private val interactor = LauncherInteractorImpl(SmartConnectApplication.get().networkProvider)
    private val sharedPrefsProvider = SmartConnectApplication.get().sharedPreferencesProvider

    override val loadingViewLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val serverCheckLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val errorLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val isUserSignedInLiveData: MutableLiveData<UserModel> by lazy { MutableLiveData<UserModel>() }
    override val loginUserLiveData: MutableLiveData<LoginUserModel> by lazy { MutableLiveData<LoginUserModel>() }

    override fun serverCheck() {
        uiScope.launch {
            loadingViewLiveData.postValue(true)

            val serverCheckResponse = executeNetworkCall {
                interactor.serverCheck()
            }

            serverCheckResponse.data?.let {
                Timber.d(it.serverName)
                serverCheckLiveData.postValue(true)
            } ?: serverCheckResponse.error.let {
                Timber.d(it?.errorMessage)
                errorLiveData.postValue(it?.errorMessage)
            }

            loadingViewLiveData.postValue(false)
        }
    }

    override fun checkUserSignIn() {
        val userEmail = sharedPrefsProvider.getString(LOGIN_USER_EMAIL, "") ?: ""
        val userPassword = sharedPrefsProvider.getString(LOGIN_USER_PASSWORD, "") ?: ""

        if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
            // silent login user
            isUserSignedInLiveData.postValue(UserModel(email = userEmail, password = userPassword))
        } else {
            // go to login page
            isUserSignedInLiveData.postValue(UserModel())
        }
    }

    override fun silentLoginUser(email: String, password: String) {
        uiScope.launch {
            loadingViewLiveData.postValue(true)
            val loginUserRequestsBody = LoginUserRequestBody(email, password)

            val response = executeNetworkCall {
                interactor.silentLoginUserAsync(loginUserRequestsBody)
            }

            response.data?.let {
                loginUserLiveData.postValue(it)
                loadingViewLiveData.postValue(false)
            } ?: response.error.let {
                Timber.d(it?.errorMessage)
                loadingViewLiveData.postValue(false)
                errorLiveData.postValue(it?.errorMessage)
            }
        }
    }
}