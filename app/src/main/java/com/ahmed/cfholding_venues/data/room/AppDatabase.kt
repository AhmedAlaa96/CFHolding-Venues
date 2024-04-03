package com.ahmed.cfholding_venues.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmed.cfholding_venues.data.models.dao.UsersDao
import com.ahmed.cfholding_venues.data.models.dto.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}