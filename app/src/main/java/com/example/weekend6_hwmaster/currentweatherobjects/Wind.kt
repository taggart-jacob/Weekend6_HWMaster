package com.example.weekend6_hwmaster.currentweatherobjects

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Wind : Parcelable {

    @SerializedName("speed")
    @Expose
    var speed: Float? = null
    @SerializedName("deg")
    @Expose
    var deg: Int? = null

    protected constructor(`in`: Parcel) {
        this.speed = `in`.readValue(Float::class.java.classLoader) as Float
        this.deg = `in`.readValue(Int::class.java.classLoader) as Int
    }

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param speed
     * @param deg
     */
    constructor(speed: Float?, deg: Int?) : super() {
        this.speed = speed
        this.deg = deg
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(speed)
        dest.writeValue(deg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Wind> = object : Creator<Wind> {


            override fun createFromParcel(`in`: Parcel): Wind {
                return Wind(`in`)
            }

            override fun newArray(size: Int): Array<Wind?> {
                return arrayOfNulls(size)
            }

        }
    }

}
