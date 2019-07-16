package com.example.weekend6_hwmaster

import com.example.weekend6_hwmaster.currentweatherobjects.WeatherObject
import com.example.weekend6_hwmaster.forecastweatherobject.ForecastObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val BASE_URL = "https://api.openweathermap.org/"
const val PATHCURRENT = "data/2.5/weather"
const val PATHFORECAST = "data/2.5/forecast"
const val QUERY_ZIP = "zip"
const val QUERY_KEY = "appid"

class RetrofitWeather {
    //COME BACK TO THIS IF THERE ARE ISSUES


    fun getRetroFitInstance(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()

        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCurrentWeatherService(): RetrofitZIPCurrentApiService {
        return getRetroFitInstance().create(RetrofitZIPCurrentApiService::class.java)
    }

    interface RetrofitZIPCurrentApiService {
        @GET(PATHCURRENT)
        fun getCurrentWeather(
            @Query(QUERY_ZIP) zip: String,
            @Query(QUERY_KEY) key: String
        ): Call<WeatherObject>
    }

    fun getFiveDayForecast(): RetrofitForecastWeather{
        return getRetroFitInstance().create(RetrofitForecastWeather::class.java)
    }

    interface RetrofitForecastWeather{
        @GET(PATHFORECAST)
        fun getForecast(
            @Query(QUERY_ZIP) zip: String,
            @Query(QUERY_KEY) key: String
        ): Call<ForecastObject>
    }
}