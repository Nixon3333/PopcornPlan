package com.drygin.popcornplan.features.sync.domain.usecase

import com.drygin.popcornplan.features.sync.domain.repository.SyncRepository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class SyncUseCase(
    private val syncRepository: SyncRepository
) {
    suspend operator fun invoke() = syncRepository.getInitialSync()
}