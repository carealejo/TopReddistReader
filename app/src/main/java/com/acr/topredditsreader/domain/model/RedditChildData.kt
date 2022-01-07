package com.acr.topredditsreader.domain.model

import android.os.Parcel
import android.os.Parcelable

data class RedditChildData (
    val title: String?,
    val thumbnail: String?,
    val url_overridden_by_dest: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(thumbnail)
        parcel.writeString(url_overridden_by_dest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RedditChildData> {
        override fun createFromParcel(parcel: Parcel): RedditChildData {
            return RedditChildData(parcel)
        }

        override fun newArray(size: Int): Array<RedditChildData?> {
            return arrayOfNulls(size)
        }
    }
}