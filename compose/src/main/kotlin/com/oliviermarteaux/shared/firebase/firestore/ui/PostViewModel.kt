package com.oliviermarteaux.shared.firebase.firestore.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PostViewModel @Inject constructor() : ViewModel() {

    var post: Post by mutableStateOf(Post())
        private set

    fun selectPost(selectedPost: Post) {
        post = selectedPost
    }
}