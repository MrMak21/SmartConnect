package gr.makris.smartconnect.vm.login

import androidx.lifecycle.MutableLiveData
import gr.makris.smartconnect.model.users.LoginUserModel

interface LoginViewModel {

    val loadingViewLiveData: MutableLiveData<Boolean>
    val loginUserLiveData: MutableLiveData<LoginUserModel>
    val errorLiveData: MutableLiveData<String>

    fun loginUser(email: String, password: String)
    fun getUsersAsync()
    fun loginGoogleUser(idToken: String)
}