package gr.makris.smartconnect.vm.launcher

import androidx.lifecycle.MutableLiveData

interface LauncherViewModel {

    val loadingViewLiveData: MutableLiveData<Boolean>
    val goToNextScreenLiveData: MutableLiveData<Boolean>
    val errorLiveData: MutableLiveData<String>

    fun serverCheck()
}