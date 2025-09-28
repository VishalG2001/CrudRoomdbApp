package com.crudapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val updatedAt: Long = System.currentTimeMillis(),
    val authorName : String
)