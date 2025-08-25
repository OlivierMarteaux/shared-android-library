package com.oliviermarteaux.shared.di

import com.oliviermarteaux.shared.data.DataRepository
import com.oliviermarteaux.shared.data.WebDataRepository
import com.oliviermarteaux.shared.utils.Logger

class WebAppContainer<T,D>(
    private val apiServiceGetData: suspend () -> List<D>,
    private val mapper: (D) -> T,
    private val log: Logger
): AppContainer<T> {

    override val dataRepository: DataRepository<T> by lazy {
        WebDataRepository(
            apiServiceGetData = apiServiceGetData,
            mapper = mapper,
            log = log
        )
    }
}