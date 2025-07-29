package com.drygin.popcornplan.features.auth.domain.usecase

import com.drygin.popcornplan.features.auth.domain.repository.AuthRepository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String/*, password: String*/): Boolean {
        return repository.login(username/*, password*/)
    }
}