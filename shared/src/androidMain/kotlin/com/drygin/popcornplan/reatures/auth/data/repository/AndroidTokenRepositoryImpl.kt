package com.drygin.popcornplan.reatures.auth.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import com.drygin.popcornplan.reatures.auth.data.datastore.tokenDataStore
import com.drygin.popcornplan.reatures.auth.data.datastore.TokenKeys
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class AndroidTokenRepositoryImpl(
    private val context: Context
) : TokenRepository {

    override suspend fun getAccessToken(): String? {
        return context.tokenDataStore.data
            .map { it[TokenKeys.accessToken] }
            .firstOrNull()
    }

    override suspend fun saveAccessToken(token: String) {
        context.tokenDataStore.edit { prefs ->
            prefs[TokenKeys.accessToken] = token
        }
    }

    override suspend fun clear() {
        context.tokenDataStore.edit { it.clear() }
    }
}