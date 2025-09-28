package com.crudapp.data

import com.crudapp.data.local.Post
import com.crudapp.data.local.PostDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val dao: PostDao
) {
    fun allPosts(): Flow<List<Post>> = dao.getAllPosts()
    fun getPost(id: Long): Flow<Post?> = dao.getPostById(id)

    suspend fun createPost(title: String, content: String, authorName: String): Long {
        val p = Post(title = title.trim(), content = content.trim(), authorName = authorName)
        return dao.insert(p)
    }

    suspend fun updatePost(post: Post) {
        dao.update(post.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun deletePost(post: Post) {
        dao.delete(post)
    }

    suspend fun upvote(post: Post) {
        val updated = post.copy(likes = post.likes + 1, updatedAt = System.currentTimeMillis())
        dao.update(updated)
    }

    suspend fun downvote(post: Post) {
        val updated =
            post.copy(dislikes = post.dislikes + 1, updatedAt = System.currentTimeMillis())
        dao.update(updated)
    }
}
