package gr.makris.smartconnect.application

import android.app.Application
import gr.makris.smartconnect.retrofit.NetworkProvider
import gr.makris.smartconnect.retrofit.NetworkProviderImpl

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

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}