package com.example.tictoccroc.repositories.remote.results

import android.os.Parcelable
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.models.server.SubwayStation
import com.google.gson.annotations.SerializedName

@kotlinx.parcelize.Parcelize
data class GetSubwayResult (
    @SerializedName("result_msg") val resultMsg: String? = null,
    @SerializedName("result_code") val resultCode: Int? = null,
    @SerializedName("version") val version: Int? = null,
    @SerializedName("subway_stations") val subwayStations: List<SubwayStation>? = null,
    @SerializedName("subway_lines") val subwayLines: List<SubwayLine>? = null,
) : Parcelable