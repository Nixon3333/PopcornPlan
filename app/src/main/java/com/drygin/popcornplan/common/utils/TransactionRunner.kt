package com.drygin.popcornplan.common.utils

import androidx.room.withTransaction
import com.drygin.popcornplan.common.data.local.AppDatabase
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 06.06.2025.
 */
class TransactionRunner @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun run(block: suspend () -> Unit) = db.withTransaction {
        block()
    }
}