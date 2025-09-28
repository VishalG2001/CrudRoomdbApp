package com.crudapp.ui.post_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crudapp.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repo: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId: Long = checkNotNull(savedStateHandle.get<Long>("postId"))

    val post = repo.getPost(postId)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun deletePost(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            val p = post.value
            if (p != null) {
                repo.deletePost(p)
                onSuccess()
            } else {
                onError(IllegalStateException("Post not found"))
            }
        }
    }

    fun onUpvote() = viewModelScope.launch {
        post.value?.let { repo.upvote(it) }
    }

    fun onDownvote() = viewModelScope.launch {
        post.value?.let { repo.downvote(it) }
    }
}
