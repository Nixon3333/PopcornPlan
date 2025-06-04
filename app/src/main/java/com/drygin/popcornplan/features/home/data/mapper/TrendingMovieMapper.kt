package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.data.model.MovieDto
import com.drygin.popcornplan.features.home.data.model.TrendingMovieDto

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
fun TrendingMovieDto.toMovieDto(): MovieDto {
    return movie.copy(watchers = watchers)
}
