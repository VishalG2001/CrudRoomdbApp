package com.crudapp.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crudapp.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditCreatePostViewModel @Inject constructor(
    private val repo: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val existingId = savedStateHandle.get<Long>("postId") // null => create

    val title = MutableStateFlow("")
    val content = MutableStateFlow("")
    val authorName = MutableStateFlow("")
    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success


    val errorMessage = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            existingId?.let { id ->
                repo.getPost(id).collect { p ->
                    p?.let {
                        title.value = it.title
                        content.value = it.content
                        authorName.value = it.authorName
                    }
                }
            }
        }
    }

    fun validateAndSave() {
        val t = title.value.trim()
        val c = content.value.trim()
        val a = authorName.value.trim()

        if (t.isEmpty()) {
            errorMessage.value = "Title can't be empty"
            return
        }
        if (c.length < 5) {
            errorMessage.value = "Content should have at least 5 characters"
            return
        }
        if (a.isEmpty()) {
            errorMessage.value = "Author can't be empty"
            return
        }

        viewModelScope.launch {
            try {
                if (existingId == null) {
                    repo.createPost(t, c, a)
                } else {
                    repo.getPost(existingId).collect { p ->
                        if (p != null) {
                            repo.updatePost(
                                p.copy(
                                    title = t,
                                    content = c,
                                    authorName = a,
                                    updatedAt = System.currentTimeMillis(),
                                )
                            )
                        }
                    }
                }
                _success.value = true
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Save failed"
            }
        }
        _success.value = true
    }

    fun resetSuccess() {
        _success.value = false
    }
}
