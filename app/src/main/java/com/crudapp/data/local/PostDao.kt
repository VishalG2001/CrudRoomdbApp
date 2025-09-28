package com.crudapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post): Long

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post: Post)

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    fun getPostById(id: Long): Flow<Post?>

    // Order by score desc, then updatedAt desc
    @Query("""
        SELECT * FROM posts
        ORDER BY (likes - dislikes) DESC, updatedAt DESC
    """)
    fun getAllPosts(): Flow<List<Post>>
}
