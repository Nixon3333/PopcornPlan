package com.drygin.popcornplan.data.repository

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.features.trending.data.remote.api.MovieApi
import com.drygin.popcornplan.data.local.AppDatabase
import com.drygin.popcornplan.domain.repository.MovieRepository
import com.drygin.popcornplan.reatures.trending.data.repository.TrendingMovieSaver

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class MovieRepositoryImpl(
    private val api: MovieApi,
    private val database: AppDatabase,
    private val trendingSaver: TrendingMovieSaver
) : MovieRepository {
    /*private var pagingSource: PagingSource<Int, TrendingWithMovie>? = null

    @OptIn(ExperimentalPagingApi::class)
    override fun getTrendingMoviesPaging(): Flow<PagingData<TrendingMovie>> {
        val pagingSourceFactory = {
            val source = database.trendingDao().getTrendingMoviesPaging()
            pagingSource = source
            source
        }

        return Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 15,
                prefetchDistance = 5
            ),
            remoteMediator = TrendingMoviesRemoteMediator(api, trendingSaver),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData: PagingData<TrendingWithMovie> ->
            pagingData.map { trendingWithMovie ->
                val images = database.imageDao().getImagesForMediaSync(trendingWithMovie.movie.traktId)
                TrendingMovieWithImages(
                    trendingMovie = trendingWithMovie.trendingMovie,
                    movie = trendingWithMovie.movie,
                    images = images
                ).toDomain()
            }
        }
    }*/

    override suspend fun getTopTrending(limit: Int): List<TrendingMovie> {
        val response = api.getTrendingMovies(limit = limit)
        return trendingSaver.saveTrendingMovies(response)
    }
}