package com.example.tictoccroc.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.tictoccroc.models.client.SearchedStation
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.models.server.SubwayStation
import com.example.tictoccroc.repositories.IRepository
import com.example.tictoccroc.repositories.local.dbs.SubwayStationDb
import com.example.tictoccroc.viewmodels.utils.RxViewModel
import com.example.tictoccroc.viewmodels.utils.ScheduleProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: IRepository,
    private val scheduleProvider: ScheduleProvider,
    private val local: SubwayStationDb,
): RxViewModel() {
    val searchResult = MutableLiveData<List<SubwayStation>>()
    var stations: List<SubwayStation> = emptyList()
    var lines: List<SubwayLine> = emptyList()

    fun fetch(){
        launch {
            repo.fetchSubwayInfos()
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe(
                    { result ->
                        if(result.resultMsg == "success") {
                            if(result.subwayLines != null && result.subwayLines.isNotEmpty() &&
                                result.subwayStations != null && result.subwayStations.isNotEmpty()){

                                result.subwayStations.forEach { station ->
                                    station.subwayLinesString = station.subwayLinesInt?.joinToString(",")
                                }

                                local.subwayLineDao().insertAll(result.subwayLines)
                                    .andThen(local.subwayStationDao().insertAll(result.subwayStations))
                                    .observeOn(scheduleProvider.ui())
                                    .subscribeOn(scheduleProvider.io())
                                    .subscribe()

                                stations = result.subwayStations
                                lines = result.subwayLines
                            }

                            getSearchedStationList()
                        }
                    },
                    { throwable ->
                        throwable.printStackTrace()
                    }
                )
        }
    }

    private fun getSearchedStationList(){
        local.searchedStationDao().getAll()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe { list ->
                val searchedStationList: MutableList<SubwayStation> = mutableListOf()

                list.forEach { searched ->
                    stations.filter { station ->
                        station.stationId == searched.stationId
                    }.map { station ->
                        val lineObjList: MutableList<SubwayLine> = mutableListOf()

                        station.subwayLinesInt?.forEach { lineNumber ->
                            lines.filter { line ->
                                line.lineId == lineNumber
                            }.forEach { line ->
                                lineObjList.add(line)
                            }
                        }

                        searchedStationList.add(
                            station.apply {
                                station.subwayLines = lineObjList

                            }
                        )
                    }
                }

                searchResult.value = searchedStationList
            }
    }

    fun delete(item: SubwayStation){
        local.searchedStationDao().delete(SearchedStation(item.stationId ?: 0))
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe {
                getSearchedStationList()
            }

    }

    fun deleteAll(){
        local.searchedStationDao().deleteAll()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe {
                getSearchedStationList()
            }
    }
}