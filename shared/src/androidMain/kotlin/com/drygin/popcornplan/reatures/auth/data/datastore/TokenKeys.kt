package com.drygin.popcornplan.reatures.auth.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
object TokenKeys {
    val accessToken = stringPreferencesKey("access_token")
    val refreshToken = stringPreferencesKey("refresh_token")
}