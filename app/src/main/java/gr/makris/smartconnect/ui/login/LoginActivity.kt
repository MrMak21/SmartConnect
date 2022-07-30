package gr.makris.smartconnect.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import gr.makris.smartconnect.MainActivity
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.databinding.ActivityLoginBinding
import gr.makris.smartconnect.databinding.ActivityMainBinding
import gr.makris.smartconnect.interactor.login.LoginInteractorImpl
import gr.makris.smartconnect.model.Definitions.LOGIN_USER_MODEL_PARCELABLE
import gr.makris.smartconnect.model.users.LoginUserModel
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

    private val GOOGLE_SIGN_IN_REQUEST = 1

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

        binding.signInButton.setOnClickListener {
            googleSignIn()
        }

        dialogDelegate.showDialog(
            title = "Error" ,
            contentText = "Hello smart connect",
            mainButtonText = "OK",
            mainButtonCallBack = {
                it.dismiss()
            }
        )
    }

    private fun initObservers() {
        val loadingObserver = Observer<Boolean> {
            binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
        }

        val userLoginObserver = Observer<LoginUserModel> {
            goToMainActivity(it)
        }

        val errorObserver = Observer<String> {
            dialogDelegate.showDialog(contentText = it)
        }

        vm.loadingViewLiveData.observe(this, loadingObserver)
        vm.loginUserLiveData.observe(this, userLoginObserver)
        vm.errorLiveData.observe(this, errorObserver)
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("168238449623-7ls85bpa16ina2mh7i8nk42g5794r3mc.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_REQUEST) {
            // The Task returned from this call is always completed, no need to attach a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account.idToken?.let {
                vm.loginGoogleUser(it)
            }
        } catch (e: ApiException) {
            Timber.d("signInResult:failed code=" + e.statusCode)
        }
    }

    private fun goToMainActivity(loginUserModel: LoginUserModel) {
        val intent  = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(LOGIN_USER_MODEL_PARCELABLE, loginUserModel)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivitySlideUp(intent)
    }
}