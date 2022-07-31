package gr.makris.smartconnect.vm.launcher

import androidx.lifecycle.MutableLiveData
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.model.users.UserModel

interface LauncherViewModel {

    val loadingViewLiveData: MutableLiveData<Boolean>
    val serverCheckLiveData: MutableLiveData<Boolean>
    val errorLiveData: MutableLiveData<String>
    val isUserSignedInLiveData: MutableLiveData<UserModel>
    val loginUserLiveData: MutableLiveData<LoginUserModel>

    fun serverCheck()
    fun checkUserSignIn()
    fun silentLoginUser(email: String, password: String)
}