package gr.makris.smartconnect.vm.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gr.makris.smartconnect.interactor.base.SmartConnectBaseInteractor
import gr.makris.smartconnect.vm.base.BaseViewModel

class CustomViewModelFactory(
    private var application: Application,
    private var interactor: SmartConnectBaseInteractor
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass as T
    }
}