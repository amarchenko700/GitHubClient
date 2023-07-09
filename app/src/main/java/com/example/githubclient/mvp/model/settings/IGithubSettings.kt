package com.example.githubclient.mvp.model.settings

import com.example.githubclient.mvp.model.entity.Settings
import com.example.githubclient.mvp.model.entity.room.Database

interface IGithubSettings {
    fun saveSettings(db: Database, settings: Settings)
    fun getSettings(db: Database): Settings
}