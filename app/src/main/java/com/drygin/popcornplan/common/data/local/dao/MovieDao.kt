package com.drygin.popcornplan.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.relation.MovieWithImages
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun movies(): Flow<List<MovieWithImages>>

    @Query("SELECT * FROM movies WHERE traktId IN (:ids)")
    fun getMoviesByIds(ids: List<Int>): List<MovieEntity>

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE traktId =:traktId")
    fun getMovie(traktId: Int): MovieEntity?

    @Transaction
    @Query("SELECT * FROM movies WHERE traktId =:traktId")
    fun movieWithImages(traktId: Int): MovieWithImages?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("delete from movies")
    suspend fun clearAll()
}