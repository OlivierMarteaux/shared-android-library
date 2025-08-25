package com.oliviermarteaux.shared.di

import com.oliviermarteaux.shared.data.DataRepository

interface AppContainer<T> {

    val dataRepository: DataRepository<T>
}