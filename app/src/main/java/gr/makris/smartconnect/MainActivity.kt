package gr.makris.smartconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.databinding.ActivityMainBinding
import gr.makris.smartconnect.vm.main.MainViewModel
import gr.makris.smartconnect.vm.main.MainViewModelImpl
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        vm = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(SmartConnectApplication.get()))[MainViewModelImpl::class.java]
    }
}