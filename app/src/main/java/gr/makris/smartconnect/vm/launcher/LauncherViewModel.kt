package gr.makris.smartconnect.vm.launcher

import androidx.lifecycle.MutableLiveData

interface LauncherViewModel {

    val loadingViewLiveData: MutableLiveData<Boolean>
    fun serverCheck()
}