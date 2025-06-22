package com.drygin.popcornplan.common.domain.usecase

import com.drygin.popcornplan.common.domain.repository.IFavoriteRepository
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 17.06.2025.
 */
class ToggleFavoriteUseCase @Inject constructor(
    private val repository: IFavoriteRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.onToggleFavorite(movieId)
    }
}
