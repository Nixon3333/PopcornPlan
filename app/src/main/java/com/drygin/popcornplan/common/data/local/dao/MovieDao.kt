package com.drygin.popcornplan.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Dao
interface MovieDao {
    @Query("select * from movies")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("select * from movies where id =:movieId")
    fun getMovie(movieId: Int): MovieEntity?

    @Query("select * from movies where watchers != 0")
    fun getTrendingMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("delete from movies")
    suspend fun clearAll()
}