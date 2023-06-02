package com.example.nasiyauz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasiyauz.data.models.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): UserDao

}