package com.drygin.popcornplan.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drygin.popcornplan.common.data.local.entity.ImageEntity

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Query("DELETE FROM images WHERE mediaTraktId = :mediaTraktId")
    suspend fun deleteImagesForMedia(mediaTraktId: Int)
}