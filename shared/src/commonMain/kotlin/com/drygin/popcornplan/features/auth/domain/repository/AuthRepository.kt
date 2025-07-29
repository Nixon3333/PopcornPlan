package com.drygin.popcornplan.features.auth.domain.repository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface AuthRepository {
    suspend fun login(username: String/*, password: String*/): Boolean
}