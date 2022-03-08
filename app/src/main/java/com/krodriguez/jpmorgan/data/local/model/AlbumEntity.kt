package com.krodriguez.jpmorgan.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krodriguez.jpmorgan.data.local.model.DBConstants.ALBUM_TABLE
import com.krodriguez.jpmorgan.data.local.model.DBConstants.TITLE_KEY
import com.krodriguez.jpmorgan.data.local.model.DBConstants.USER_ID_KEY

@Entity(tableName = ALBUM_TABLE)
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = USER_ID_KEY) var userId: Int = 0,
    @ColumnInfo(name = TITLE_KEY) var title: String = ""
)
