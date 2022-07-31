package gr.makris.smartconnect.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gr.makris.smartconnect.MainActivity
import gr.makris.smartconnect.R
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.databinding.ActivityLauncherBinding
import gr.makris.smartconnect.databinding.ActivityLoginBinding
import gr.makris.smartconnect.model.Definitions
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.ui.base.BaseActivity
import gr.makris.smartconnect.ui.login.LoginActivity
import gr.makris.smartconnect.vm.launcher.LauncherViewModel
import gr.makris.smartconnect.vm.launcher.LauncherViewModelImpl
import gr.makris.smartconnect.vm.login.LoginViewModel
import gr.makris.smartconnect.vm.login.LoginViewModelImpl

class LauncherActivity : BaseActivity() {

    lateinit var binding: ActivityLauncherBinding
    private lateinit var vm: LauncherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(
                SmartConnectApplication.get()
            )
        )[LauncherViewModelImpl::class.java]


        initLayout()
        initObservers()
    }

    private fun initLayout() {
        vm.serverCheck()
    }

    private fun initObservers() {
        val loadingObserver = Observer<Boolean> {
            binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
        }

        val serverCheckObserver = Observer<Boolean> {
            if (it) {
                vm.checkUserSignIn()
            }
        }

        val errorObserver = Observer<String> {
            dialogDelegate.showDialog(contentText = it)
        }

        val userLoginObserver = Observer<LoginUserModel> {
            goToMainActivity(it)
        }

        val userSignedInObserver = Observer<UserModel> {
            if (it == UserModel()) {
                goToLoginActivity()
            } else {
                vm.silentLoginUser(it.email, it.password)
            }
        }

        vm.loadingViewLiveData.observe(this, loadingObserver)
        vm.serverCheckLiveData.observe(this, serverCheckObserver)
        vm.errorLiveData.observe(this, errorObserver)
        vm.isUserSignedInLiveData.observe(this, userSignedInObserver)
        vm.loginUserLiveData.observe(this, userLoginObserver)
    }

    private fun goToMainActivity(loginUserModel: LoginUserModel) {
        val intent  = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Definitions.LOGIN_USER_MODEL_PARCELABLE, loginUserModel)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivitySlideUp(intent)
    }

    private fun goToLoginActivity() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivitySlideUp(loginIntent)
    }


}