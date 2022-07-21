package gr.makris.smartconnect.vm.login

import androidx.lifecycle.MutableLiveData

interface LoginViewModel {

    val loadingViewLiveData: MutableLiveData<Boolean>

    fun loginUser(email: String, password: String)
    fun getUsersAsync()
}