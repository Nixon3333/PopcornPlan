package com.drygin.popcornplan.common.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.entity.MovieWithImages
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Dao
interface MovieDao {
    @Query("select * from movies")
    fun movies(): Flow<List<MovieWithImages>>

    @Query("select * from movies where traktId in (:ids)")
    fun getMoviesByIds(ids: List<Int>): List<MovieEntity>

    @Query("SELECT * FROM movies ORDER BY pageIndex ASC, watchers DESC")
    fun getPagedMovies(): PagingSource<Int, MovieWithImages>

    @Query("select * from movies where traktId =:traktId")
    fun getMovie(traktId: Int): MovieEntity?

    @Transaction
    @Query("select * from movies where traktId =:traktId")
    fun movieWithImages(traktId: Int): MovieWithImages?

    @Query("select * from movies where watchers != 0")
    fun getTrendingMovies(): Flow<List<MovieWithImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("delete from movies")
    suspend fun clearAll()
}