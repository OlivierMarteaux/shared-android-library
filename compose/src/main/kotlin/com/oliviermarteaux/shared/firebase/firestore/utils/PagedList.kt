package com.oliviermarteaux.shared.firebase.firestore.utils

import com.google.firebase.firestore.DocumentSnapshot

data class PagedList<T>(
    val items: List<T>,
    val lastSnapshot: DocumentSnapshot?,
    val isLastPage: Boolean
)