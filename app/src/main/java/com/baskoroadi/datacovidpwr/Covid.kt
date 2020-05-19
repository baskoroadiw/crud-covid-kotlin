package com.baskoroadi.datacovidpwr

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Covid(
    var odp: String? = null,
    var pdp: String? = null,
    var positif: String? = null,
    var date: String? = null
) : Parcelable