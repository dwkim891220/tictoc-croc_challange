package com.example.tictoccroc.repositories

import com.example.tictoccroc.repositories.remote.TicTocCrocApiService
import com.example.tictoccroc.repositories.remote.results.GetSubwayResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: TicTocCrocApiService
): IRepository {
    override fun fetchSubwayInfos(): Single<GetSubwayResult> = apiService.fetchSubwayInfos()
}