package com.oliviermarteaux.shared.data

import com.oliviermarteaux.shared.utils.Logger

class WebDataRepository<T, D>(
    private val apiServiceGetData: suspend () -> List<D>,
    private val mapper: (D) -> T,
    private val log: Logger
) : DataRepository<T> {
    override suspend fun getData(): Result<List<T>> = try {
        log.d("WebDataRepository: Api call successful")
        Result.success(apiServiceGetData().map(mapper))
    } catch (e: Exception) {
        log.d("WebDataRepository: Api call failed. Reason: ${e.message}")
        Result.failure(e)
    }
}