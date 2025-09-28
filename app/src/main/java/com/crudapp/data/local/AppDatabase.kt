package com.crudapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
