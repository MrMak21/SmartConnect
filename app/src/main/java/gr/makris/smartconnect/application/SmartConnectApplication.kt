package gr.makris.smartconnect.application

import android.app.Application
import gr.makris.smartconnect.BuildConfig
import gr.makris.smartconnect.common.delegates.DialogDelegate
import gr.makris.smartconnect.provider.sharedPreferences.DefaultSharedPreferencesProvider
import gr.makris.smartconnect.provider.sharedPreferences.SharedPreferencesProvider
import gr.makris.smartconnect.retrofit.NetworkProvider
import gr.makris.smartconnect.retrofit.NetworkProviderImpl
import timber.log.Timber

class SmartConnectApplication : Application() {

    companion object {
        private lateinit var instance: SmartConnectApplication

        @JvmStatic
        fun get(): SmartConnectApplication {
            return instance
        }
    }

    val networkProvider: NetworkProvider by lazy {
        return@lazy NetworkProviderImpl()
    }

    val sharedPreferencesProvider: SharedPreferencesProvider by lazy {
        return@lazy DefaultSharedPreferencesProvider(this).apply { init() }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        initTimber()

    }

    fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}