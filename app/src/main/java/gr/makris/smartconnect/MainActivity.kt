package gr.makris.smartconnect

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.databinding.ActivityMainBinding
import gr.makris.smartconnect.vm.main.MainViewModel
import gr.makris.smartconnect.vm.main.MainViewModelImpl
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var vm: MainViewModel

    private val GOOGLE_SIGN_IN_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(SmartConnectApplication.get()))[MainViewModelImpl::class.java]

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("168238449623-7ls85bpa16ina2mh7i8nk42g5794r3mc.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        binding.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST)
        }

        binding.getUsersBtn.setOnClickListener {
            getUsers()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_REQUEST) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    fun getUsers() {
        vm.getUsers()
    }
}