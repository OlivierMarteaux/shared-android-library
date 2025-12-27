package com.oliviermarteaux.shared.firebase.firestore.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.shared.utils.downloadImageToLocalFile
import com.oliviermarteaux.shared.utils.randomAvatarUrl
import com.oliviermarteaux.shared.utils.randomPhoto

@RequiresApi(Build.VERSION_CODES.O)
suspend fun uploadSamplePosts(context: Context, upload: suspend (Post) -> Result<Unit>){
    val posts = generateSamplePosts()
    posts.forEach {

        val internetPhotoUrl = it.photoUrl?: randomPhoto()
        Log.d("OM_TAG", "uploadSamplePosts: internetPhotoUrl = $internetPhotoUrl")
        val localPhotoUrl = downloadImageToLocalFile(context,  internetPhotoUrl)

        val randomAvatarUrl: String = randomAvatarUrl()
        Log.d("OM_TAG", "uploadSamplePosts: randomAvatarUrl = $randomAvatarUrl")
        val internetAvatarUrl = if((it.author?.photoUrl ?: "").isEmpty()) randomAvatarUrl else it.author?.photoUrl?:""
        Log.d("OM_TAG", "uploadSamplePosts: internetAvatar = $internetAvatarUrl")
        val localAvatarUrl = downloadImageToLocalFile(context,  internetAvatarUrl)

        val post = it.copy(
            photoUrl = localPhotoUrl.toString(),
            author = it.author?.copy(photoUrl = localAvatarUrl.toString())
        )

        upload(post)
    }
}