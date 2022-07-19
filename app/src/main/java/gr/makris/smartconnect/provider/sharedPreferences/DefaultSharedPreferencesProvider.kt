package gr.makris.smartconnect.provider.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import timber.log.Timber

class DefaultSharedPreferencesProvider(var appContext: Context?) : SharedPreferencesProvider {

    private lateinit var preferences: SharedPreferences

    override fun init(preferencesFileName: String, mode: Int) {
        Timber.d(
            "DefaultSharedPreferencesProvider > init > (mode = %s, preferencesFileName = %s) called",
            mode,
            preferencesFileName
        )
        try {
            preferences = appContext?.getSharedPreferences(preferencesFileName, mode)!!
        } catch (e: Exception) {
            Timber.d(
                e,
                "DefaultSharedPreferencesProvider > init > (mode = %s, preferencesFileName = %s) failed",
                mode,
                preferencesFileName
            )
        } finally {
            appContext = null
        }
    }

    @Synchronized
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val result = preferences.getBoolean(key, defaultValue)
        Timber.d(
            "DefaultSharedPreferencesProvider > getBoolean > (key = %s, defaultValue = %s) => result = %s",
            key,
            defaultValue,
            result
        )
        return result
    }

    @Synchronized
    override fun putBoolean(key: String, value: Boolean): Boolean {
        val result = preferences.edit().putBoolean(key, value).commit()
        Timber.d(
            "DefaultSharedPreferencesProvider > putBoolean > (key = %s, value = %s) => result = %s",
            key,
            value,
            result
        )
        return result
    }

    @Synchronized
    override fun getFloat(key: String, defaultValue: Float): Float {
        val result = preferences.getFloat(key, defaultValue)
        Timber.d(
            "DefaultSharedPreferencesProvider > getFloat > (key = %s, defaultValue = %s) => result = %s",
            key,
            defaultValue,
            result
        )
        return result
    }

    @Synchronized
    override fun putFloat(key: String, value: Float): Boolean {
        val result = preferences.edit().putFloat(key, value).commit()
        Timber.d(
            "DefaultSharedPreferencesProvider > putFloat > (key = %s, value = %s) => result = %s",
            key,
            value,
            result
        )
        return result
    }

    @Synchronized
    override fun getInt(key: String, defaultValue: Int): Int {
        val result = preferences.getInt(key, defaultValue)
        Timber.d(
            "DefaultSharedPreferencesProvider > getInt > (key = %s, defaultValue = %s) => result = %s",
            key,
            defaultValue,
            result
        )
        return result
    }

    @Synchronized
    override fun putInt(key: String, value: Int): Boolean {
        val result = preferences.edit().putInt(key, value).commit()
        Timber.d(
            "DefaultSharedPreferencesProvider > putInt > (key = %s, value = %s) => result = %s",
            key,
            value,
            result
        )
        return result
    }

    @Synchronized
    override fun getLong(key: String, defaultValue: Long): Long {
        val result = preferences.getLong(key, defaultValue)
        Timber.d(
            "DefaultSharedPreferencesProvider > getLong > (key = %s, defaultValue = %s) => result = %s",
            key,
            defaultValue,
            result
        )
        return result
    }

    @Synchronized
    override fun putLong(key: String, value: Long): Boolean {
        val result = preferences.edit().putLong(key, value).commit()
        Timber.d(
            "DefaultSharedPreferencesProvider > putLong > (key = %s, value = %s) => result = %s",
            key,
            value,
            result
        )
        return result
    }

    @Synchronized
    override fun getString(key: String, defaultValue: String?): String? {
        val result = preferences.getString(key, defaultValue)
        Timber.d(
            "DefaultSharedPreferencesProvider > getString > (key = %s, defaultValue = %s) => result = %s",
            key,
            defaultValue,
            result
        )
        return result
    }

    @Synchronized
    override fun putString(key: String, value: String?): Boolean {
        val result = preferences.edit().putString(key, value).commit()
        Timber.d(
            "DefaultSharedPreferencesProvider > putString > (key = %s, value = %s) => result = %s",
            key,
            value,
            result
        )
        return result
    }

    @Synchronized
    override fun getCreateString(key: String, defaultValue: String?): String? {
        if (!preferences.contains(key)) {
            putString(key, defaultValue)
        }
        return getString(key, defaultValue)
    }

    @Synchronized
    override fun remove(key: String): Boolean {
        val result = preferences.edit().remove(key).commit()
        Timber.d(
            "DefaultSharedPreferencesProvider > getString > key = %s, result = %s",
            key,
            result
        )
        return result
    }

    @Synchronized
    override fun clear(): Boolean {
        Timber.d("DefaultSharedPreferencesProvider > clear > called")
        return preferences.edit().clear().commit()
    }

    override fun resetPreferences() {
        Timber.d("DefaultSharedPreferencesProvider > resetPreferences > called")
        val prefs: Map<String?, *> = preferences.all
        for ((key) in prefs) {
            if (key == "intro_seen_key" || key == "welcome_seen_key" || key == "installation_id" || key == "push_token_key" || key == "default_language"
                ||key == "sitecoreVersion" ||key=="appRateVersion" ||key=="appRateHasViewed") {
                continue
            }
            key?.let { remove(it) }
        }
    }
}