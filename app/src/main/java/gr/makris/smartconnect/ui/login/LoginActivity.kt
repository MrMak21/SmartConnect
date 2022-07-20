package gr.makris.smartconnect.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.databinding.ActivityLoginBinding
import gr.makris.smartconnect.databinding.ActivityMainBinding
import gr.makris.smartconnect.interactor.login.LoginInteractorImpl
import gr.makris.smartconnect.ui.base.BaseActivity
import gr.makris.smartconnect.vm.factory.CustomViewModelFactory
import gr.makris.smartconnect.vm.login.LoginViewModel
import gr.makris.smartconnect.vm.login.LoginViewModelImpl
import gr.makris.smartconnect.vm.main.MainViewModel
import gr.makris.smartconnect.vm.main.MainViewModelImpl
import timber.log.Timber

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var vm: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(
            SmartConnectApplication.get()))[LoginViewModelImpl::class.java]

//        vm = ViewModelProvider(this, CustomViewModelFactory(
//            SmartConnectApplication.get(), LoginInteractorImpl(SmartConnectApplication.get().networkProvider)
//        ))[LoginViewModelImpl::class.java]

        vm.getUsersAsync()

    }
}