package com.krodriguez.jpmorgan.data.local.dao

import androidx.room.*
import com.krodriguez.jpmorgan.data.local.model.AlbumEntity
import com.krodriguez.jpmorgan.data.local.model.DBConstants

@Dao
interface AlbumsDao {

    @Query("SELECT * FROM ${DBConstants.ALBUM_TABLE} ORDER BY title ASC")
    fun getAlbums(): List<AlbumEntity>

    @Insert
    fun insertAlbum(album: AlbumEntity): Long

    @Query("DELETE FROM ${DBConstants.ALBUM_TABLE}")
    fun clearAllAlbums(): Int

}