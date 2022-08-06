package com.example.tictoccroc.repositories

import com.example.tictoccroc.repositories.remote.results.GetSubwayResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): IRepository
}

interface IRepository {
    fun fetchSubwayInfos(): Single<GetSubwayResult>
}