package com.drygin.popcornplan.preview

import androidx.paging.PagingData
import com.drygin.popcornplan.common.domain.model.Ids
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
object PreviewMocks {
    val sampleMovies = listOf(
        TrendingMovie(1, Movie(ids = Ids(trakt = 1)), 1),
        TrendingMovie(2, Movie(ids = Ids(trakt = 2)), 1),
        TrendingMovie(3, Movie(ids = Ids(trakt = 3)), 1),
        TrendingMovie(4, Movie(ids = Ids(trakt = 4)), 1),
        TrendingMovie(5, Movie(ids = Ids(trakt = 5)), 1),
        TrendingMovie(6, Movie(ids = Ids(trakt = 6)), 1),
        TrendingMovie(7, Movie(ids = Ids(trakt = 7)), 1),
        TrendingMovie(8, Movie(ids = Ids(trakt = 8)), 1),
        TrendingMovie(9, Movie(ids = Ids(trakt = 9)), 1),
        TrendingMovie(10, Movie(ids = Ids(trakt = 10)), 1),
        TrendingMovie(11, Movie(ids = Ids(trakt = 11)), 2),
        TrendingMovie(12, Movie(ids = Ids(trakt = 12)), 2),
        TrendingMovie(13, Movie(ids = Ids(trakt = 13)), 2),
        TrendingMovie(14, Movie(ids = Ids(trakt = 14)), 2),
        TrendingMovie(15, Movie(ids = Ids(trakt = 15)), 2),
        TrendingMovie(16, Movie(ids = Ids(trakt = 16)), 2),
        TrendingMovie(17, Movie(ids = Ids(trakt = 17)), 2),
        TrendingMovie(18, Movie(ids = Ids(trakt = 18)), 2),
        TrendingMovie(19, Movie(ids = Ids(trakt = 19)), 2),
        TrendingMovie(20, Movie(ids = Ids(trakt = 20)), 2),
    )

    fun <T : Any> List<T>.toPagingData(): PagingData<T> = PagingData.from(this)

    // Мок Flow для превью
    val mockedMoviesFlow: Flow<PagingData<TrendingMovie>> = flowOf(sampleMovies.toPagingData())
}