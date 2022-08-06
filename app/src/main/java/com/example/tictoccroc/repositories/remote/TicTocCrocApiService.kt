package com.example.tictoccroc.repositories.remote

import com.example.tictoccroc.constants.ApiUrls.SUBWAY
import com.example.tictoccroc.repositories.remote.results.GetSubwayResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface TicTocCrocApiService {
    @GET(SUBWAY)
    fun fetchSubwayInfos(): Single<GetSubwayResult>
}