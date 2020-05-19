package com.baskoroadi.datacovidpwr

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Covid(
    var odp: String? = "",
    var pdp: String? = "",
    var positif: String? = "",
    var date: String? = ""
) : Parcelable