package com.oliviermarteaux.shared.data

import kotlinx.coroutines.flow.Flow

interface DataRepository<T> {

    suspend fun updateItem(item: T): T

    suspend fun getDataStream(): Result<Flow<List<T>>>

    fun getItemByIdStream(id:Int): Flow<T>

}