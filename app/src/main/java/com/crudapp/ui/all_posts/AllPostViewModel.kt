package com.crudapp.ui.all_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crudapp.data.PostRepository
import com.crudapp.data.local.Post
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class AllPostViewModel @Inject constructor(
    private val repo: PostRepository
) : ViewModel() {

    val posts = repo.allPosts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onUpvote(post: Post) {
        viewModelScope.launch {
            repo.upvote(post)
        }
    }

    fun onDownvote(post: Post) {
        viewModelScope.launch {
            repo.downvote(post)
        }
    }
}
