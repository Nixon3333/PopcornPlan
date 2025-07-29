package com.drygin.popcornplan.reatures.auth.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

/**
 * Created by Drygin Nikita on 28.07.2025.
 */

val Context.tokenDataStore by preferencesDataStore(name = "token_store")