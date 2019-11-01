package com.trickyandroid.mvvmdataloading.data.models

import android.os.Parcel
import android.os.Parcelable
import java.lang.StringBuilder

data class DataModel(var test: String = "") : Parcelable {

    internal var title: StringBuilder? = null
    internal var imgUrl: StringBuilder? = null
    internal var desc: StringBuilder? = null
    internal var id: Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(title.toString())
        p0?.writeString(desc.toString())
        p0?.writeString(imgUrl.toString())
        p0?.writeInt(id!!.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(parcel: Parcel) : this(parcel.readString()) {
        this.title = StringBuilder(parcel.readString())
        this.desc = StringBuilder(parcel.readString())
        this.imgUrl = StringBuilder(parcel.readString())
        this.id = parcel.readInt()
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }


}