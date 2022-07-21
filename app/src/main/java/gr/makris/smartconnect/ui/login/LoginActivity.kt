package gr.makris.smartconnect.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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


        initLayout()
        initObservers()
    }

    private fun initLayout() {
        binding.loginBtn.setOnClickListener {
            vm.loginUser(binding.emailInput.text.toString(), binding.passwordInput.text.toString())
        }

        binding.getUsersBtn.setOnClickListener {
            vm.getUsersAsync()
        }
    }

    private fun initObservers() {
        val loadingObserver = Observer<Boolean> {
            binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
        }

        vm.loadingViewLiveData.observe(this, loadingObserver)
    }
}