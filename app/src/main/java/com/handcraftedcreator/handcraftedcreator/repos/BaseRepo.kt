package com.example.handcraftedmarket.repos

import com.handcraftedcreator.handcraftedcreator.repos.RepoResource
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent

abstract class BaseRepo<T> : KoinComponent {

    abstract suspend fun listenToRepo(): Flow<RepoResource<T>>

    protected abstract suspend fun checkCache(): T?

    protected abstract suspend fun storeToCache(t: T)
}