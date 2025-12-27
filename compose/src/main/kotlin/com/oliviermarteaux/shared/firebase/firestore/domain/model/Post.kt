package com.oliviermarteaux.shared.firebase.firestore.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.oliviermarteaux.shared.extensions.toLocalDateString
import com.oliviermarteaux.shared.extensions.toLocalTimeString
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

/**
 * This class represents a Post data object. It holds information about a post, including its
 * ID, title, description, photo URL, creation timestamp, and the author (User object).
 * The class implements Serializable to allow for potential serialization needs.
 *
 * @property id Unique identifier for the Post.
 * @property title Title of the Post.
 * @property description Optional description for the Post.
 * @property photoUrl URL of an image associated with the Post, if any.
 * @property timestamp Timestamp representing the creation date and time of the Post
 * in milliseconds since epoch.
 * @property author User object representing the author of the Post.
 * @property comments A list of comments on the post.
 * @property date the date for the post event
 * @property address the address for the post event *
 */
@RequiresApi(Build.VERSION_CODES.O)
data class Post(
    /**
     * Unique identifier for the Post.
     */
    val id: String = UUID.randomUUID().toString(),

    /**
     * Title of the Post.
     */
    val title: String = "",

    /**
     * Optional description for the Post.
     */
    val description: String = "",

    /**
     * URL of an image associated with the Post, if any.
     */
    val photoUrl: String? = "",

    /**
     * Timestamp representing the creation date and time of the Post in milliseconds since epoch.
     */
    val timestamp: Long = System.currentTimeMillis(),

    /**
     * User object representing the author of the Post.
     */
    val author: User? = User(),

    /**
     * A list of comments on the post.
     */
    val comments: List<Comment> = emptyList(),

    /**
     * the date for the post event
     */
    val date: Date? = null,
    /**
     * the time for the post event
     */
    val time: Date? = null,
    /**
     * the address for the post event
     */
    val address: Address = Address(),

    ) : Serializable {

    // Explicit no-arg constructor for Firebase deserialization --> needed for minification
    constructor() : this(
        id = UUID.randomUUID().toString(),
        title = "",
        description = "",
        photoUrl = "",
        timestamp = System.currentTimeMillis(),
        author = User(),
        comments = emptyList(),
        date = null,
        time = null,
        address = Address()
    )

    // --- Compose-friendly getters ---
    val localeDate: LocalDate?
        get() = date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()

    val localeDateString : String
        get() = localeDate?.toLocalDateString()?:""

    val localeTime: LocalTime?
        get() = time?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalTime()

    val localeTimeString : String
        get() = localeTime?.toLocalTimeString()?:""

    // --- Helpers to update date/time in Post ---
    fun copyWithLocalDate(newDate: LocalDate): Post =
        copy(date = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))

    fun copyWithLocalTime(newTime: LocalTime): Post =
        copy(time = Date.from(newTime.atDate(localeDate).atZone(ZoneId.systemDefault()).toInstant()))
}