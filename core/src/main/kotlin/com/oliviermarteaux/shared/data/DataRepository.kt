package com.oliviermarteaux.shared.data

interface DataRepository<T> {

    suspend fun getData(): Result<List<T>>
}