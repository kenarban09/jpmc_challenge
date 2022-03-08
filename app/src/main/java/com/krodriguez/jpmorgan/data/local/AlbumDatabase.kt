package com.krodriguez.jpmorgan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krodriguez.jpmorgan.data.local.dao.AlbumsDao
import com.krodriguez.jpmorgan.data.local.model.AlbumEntity
import com.krodriguez.jpmorgan.data.local.model.DBConstants

@Database(
    entities = [AlbumEntity::class],
    version = DBConstants.DATABASE_VERSION_CODE,
    exportSchema = true
)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

    companion object {
        private var instance: AlbumDatabase? = null

        fun getInstance(context: Context): AlbumDatabase? {
            if (instance == null) {
                synchronized(AlbumDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        AlbumDatabase::class.java, DBConstants.DATABASE_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}
