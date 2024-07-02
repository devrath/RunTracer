package com.istudio.core.data.networking.auth

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.istudio.core.domain.AuthInfo
import com.istudio.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences
): SessionStorage {

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }
        }
    }

    /**
     * Here it is used to set the auth info in the shared preferences
     * (1) We should be able to pass the `NULL` value also so that if we do pass it, It will re-set the preferences.
     *     It is useful when we want to clear the preferences when we log out
     * (2)
     *
     * @param info
     */
    @SuppressLint("ApplySharedPref")
    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if(info == null) {
                // We use commit since it is a blocking call
                sharedPreferences.edit().remove(KEY_AUTH_INFO).commit()
                return@withContext
            }

            // If the info is not null, We have to save an actual auth info
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            sharedPreferences
                .edit()
                .putString(KEY_AUTH_INFO, json)
                .commit()
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}