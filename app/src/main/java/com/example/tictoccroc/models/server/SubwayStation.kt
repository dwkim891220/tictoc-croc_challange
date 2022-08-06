package com.example.tictoccroc.models.server

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SubwayStation (
    @PrimaryKey @SerializedName("idx") var stationId: Int? = null,
    @SerializedName("name") var stationName: String? = null,
    @Ignore @SerializedName("subway_lines") val subwayLinesInt: List<Int>? = null,
    var subwayLinesString: String? = null,
    @Ignore var subwayLines: List<SubwayLine>? = null,
) : Parcelable