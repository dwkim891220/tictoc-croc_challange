package com.example.tictoccroc.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictoccroc.models.client.SearchedStation
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.models.server.SubwayStation
import com.example.tictoccroc.repositories.local.dbs.SubwayStationDb
import com.example.tictoccroc.viewmodels.utils.RxViewModel
import com.example.tictoccroc.viewmodels.utils.ScheduleProvider
import com.example.tictoccroc.viewmodels.utils.ViewModelState
import com.jakewharton.rxbinding4.InitialValueObservable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val local: SubwayStationDb,
    private val scheduleProvider: ScheduleProvider,
): RxViewModel() {
    private lateinit var subwayLines: List<SubwayLine>

    val searchState = MutableLiveData<ViewModelState>()
    val searchResult = MutableLiveData<List<SubwayStation>>()

    init {
        local.subwayLineDao().getAll()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe { result ->
                subwayLines = result
            }
    }

    fun initEtObservable(obs: Observable<CharSequence>){
        obs.debounce(400, TimeUnit.MILLISECONDS)
            .filter { c -> c.isNotEmpty() }
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe { c ->
                local.subwayStationDao().find(c.toString())
                    .observeOn(scheduleProvider.ui())
                    .subscribeOn(scheduleProvider.io())
                    .subscribe { list ->
                        searchResult.value = list.map { station ->
                            station.subwayLinesString
                                ?.split(",")
                                ?.toList()
                                ?.forEach { p ->
                                    val lineNumber = p.toIntOrNull()
                                    if(lineNumber != null){
                                        station.subwayLines =
                                            subwayLines.filter { line -> lineNumber == line.lineId }
                                    }
                                }

                            station
                        }
                    }
            }
    }

    fun clickStation(station: SubwayStation){
        local.searchedStationDao().insert(SearchedStation(station.stationId ?: 0))
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe()
            {
                searchState.value = ClickStationState
            }
    }
}

object ClickStationState: ViewModelState()