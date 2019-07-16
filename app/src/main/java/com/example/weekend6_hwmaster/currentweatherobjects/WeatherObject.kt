package com.example.weekend6_hwmaster.currentweatherobjects

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherObject : Parcelable {

    @SerializedName("coord")
    @Expose
    var coord: Coord? = null
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null
    @SerializedName("base")
    @Expose
    var base: String? = null
    @SerializedName("main")
    @Expose
    var main: Main? = null
    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null
    @SerializedName("dt")
    @Expose
    var dt: Int? = null
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null
    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("cod")
    @Expose
    var cod: Int? = null

    protected constructor(`in`: Parcel) {
        this.coord = `in`.readValue(Coord::class.java.classLoader) as Coord
        `in`.readList(this.weather!!, Weather::class.java.classLoader)
        this.base = `in`.readValue(String::class.java.classLoader) as String
        this.main = `in`.readValue(Main::class.java.classLoader) as Main
        this.visibility = `in`.readValue(Int::class.java.classLoader) as Int
        this.wind = `in`.readValue(Wind::class.java.classLoader) as Wind
        this.clouds = `in`.readValue(Clouds::class.java.classLoader) as Clouds
        this.dt = `in`.readValue(Int::class.java.classLoader) as Int
        this.sys = `in`.readValue(Sys::class.java.classLoader) as Sys
        this.timezone = `in`.readValue(Int::class.java.classLoader) as Int
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.cod = `in`.readValue(Int::class.java.classLoader) as Int
    }

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param dt
     * @param clouds
     * @param coord
     * @param wind
     * @param timezone
     * @param cod
     * @param visibility
     * @param sys
     * @param name
     * @param base
     * @param weather
     * @param main
     */
    constructor(
        coord: Coord,
        weather: List<Weather>,
        base: String,
        main: Main,
        visibility: Int?,
        wind: Wind,
        clouds: Clouds,
        dt: Int?,
        sys: Sys,
        timezone: Int?,
        id: Int?,
        name: String,
        cod: Int?
    ) : super() {
        this.coord = coord
        this.weather = weather
        this.base = base
        this.main = main
        this.visibility = visibility
        this.wind = wind
        this.clouds = clouds
        this.dt = dt
        this.sys = sys
        this.timezone = timezone
        this.id = id
        this.name = name
        this.cod = cod
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(coord)
        dest.writeList(weather)
        dest.writeValue(base)
        dest.writeValue(main)
        dest.writeValue(visibility)
        dest.writeValue(wind)
        dest.writeValue(clouds)
        dest.writeValue(dt)
        dest.writeValue(sys)
        dest.writeValue(timezone)
        dest.writeValue(id)
        dest.writeValue(name)
        dest.writeValue(cod)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<WeatherObject> = object : Creator<WeatherObject> {


            override fun createFromParcel(`in`: Parcel): WeatherObject {
                return WeatherObject(`in`)
            }

            override fun newArray(size: Int): Array<WeatherObject?> {
                return arrayOfNulls(size)
            }

        }
    }

}
