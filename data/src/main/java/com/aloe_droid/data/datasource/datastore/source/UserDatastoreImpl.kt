package com.aloe_droid.data.datasource.datastore.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aloe_droid.data.datasource.dto.user.UserDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class UserDatastoreImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : UserDatastore {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = USER)

    override suspend fun saveUser(userDTO: UserDTO) {
        context.datastore.edit { preferences: MutablePreferences ->
            val idKey: Preferences.Key<String> = stringPreferencesKey(USER_ID)
            val addressKey: Preferences.Key<String> = stringPreferencesKey(USER_ADDRESS)
            preferences[idKey] = userDTO.id.toString()
            preferences[addressKey] = userDTO.address
        }
    }

    override fun getUser(): Flow<UserDTO?> = context.datastore.data.map { preference ->
        val idKey: Preferences.Key<String> = stringPreferencesKey(USER_ID)
        val addressKey: Preferences.Key<String> = stringPreferencesKey(USER_ADDRESS)
        val id = preference[idKey]
        val address = preference[addressKey]
        if (id != null && address != null) {
            UserDTO(id = UUID.fromString(id), address = address)
        } else {
            null
        }
    }

    override suspend fun clearUser() {
        val idKey: Preferences.Key<String> = stringPreferencesKey(USER_ID)
        val addressKey: Preferences.Key<String> = stringPreferencesKey(USER_ADDRESS)
        context.datastore.edit { preferences: MutablePreferences ->
            preferences.remove(key = idKey)
            preferences.remove(key = addressKey)
        }
    }

    companion object {
        private const val USER = "user"
        private const val USER_ID = "user_id"
        private const val USER_ADDRESS = "user_address"
    }
}