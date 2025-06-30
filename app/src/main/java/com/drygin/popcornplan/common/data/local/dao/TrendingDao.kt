package com.drygin.popcornplan.common.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drygin.popcornplan.common.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.common.data.local.relation.TrendingMovieWithImages

/**
 * Created by Drygin Nikita on 10.06.2025.
 */
@Dao
interface TrendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TrendingMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovie(trending: TrendingMovieEntity)

    @Query("SELECT * FROM trending_movies ORDER BY pageIndex, watchers DESC")
    fun getTrendingMoviesPaging(): PagingSource<Int, TrendingMovieWithImages>
    @Query("SELECT * FROM trending_movies ORDER BY watchers DESC")
    suspend fun getTrendingMovies(): List<TrendingMovieWithImages>

    @Query("SELECT * FROM trending_movies WHERE traktId IN (:ids)")
    fun getMoviesByIds(ids: List<Int>): List<TrendingMovieWithImages>

    @Query("DELETE FROM trending_movies")
    fun clearAll()
}