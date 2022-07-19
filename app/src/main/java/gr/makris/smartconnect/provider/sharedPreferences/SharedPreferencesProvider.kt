package gr.makris.smartconnect.provider.sharedPreferences

import gr.makris.smartconnect.provider.common.Provider

interface SharedPreferencesProvider : Provider {

    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREFERENCE_FILE_NAME = "DefaultSharedPreferences"
    }

    fun init(preferencesFileName: String = PREFERENCE_FILE_NAME, mode: Int = PRIVATE_MODE)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun putBoolean(key: String, value: Boolean): Boolean

    fun getFloat(key: String, defaultValue: Float): Float

    fun putFloat(key: String, value: Float): Boolean

    fun getInt(key: String, defaultValue: Int): Int

    fun putInt(key: String, value: Int): Boolean

    fun getLong(key: String, defaultValue: Long): Long

    fun putLong(key: String, value: Long): Boolean

    fun getString(key: String, defaultValue: String?): String?

    fun putString(key: String, value: String?): Boolean

    fun getCreateString(key: String, defaultValue: String?): String?

    fun remove(key: String): Boolean

    fun clear(): Boolean

    fun resetPreferences()
}