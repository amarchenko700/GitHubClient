package com.example.githubclient.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubclient.mvp.model.entity.room.RoomSettings

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSettings(settings: RoomSettings)

    @Query("SELECT * FROM RoomSettings")
    fun getSettings(): RoomSettings?
}