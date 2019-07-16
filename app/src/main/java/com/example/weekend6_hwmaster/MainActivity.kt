package com.example.weekend6_hwmaster

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weekend6_hwmaster.currentweatherobjects.WeatherObject
import com.example.weekend6_hwmaster.forecastweatherobject.ForecastObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val PREFS_FILENAME = "prefs"
    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etZip: TextView = findViewById(R.id.etZip)
        val layout: LinearLayout = findViewById(R.id.layoutCurrentTemp)
        val tvCurrentTemp: TextView = findViewById(R.id.tvCurrentTemperature)
        val tvCity: TextView = findViewById(R.id.tvCity)
        prefs = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val strSharedZip: String? = prefs!!.getString("zip", "NO ZIP FOUND")

        if (!strSharedZip.equals("NO ZIP FOUND")) {
            layout.visibility = View.VISIBLE
            rvForecast.visibility = View.VISIBLE
            tvCity.visibility = View.VISIBLE
            btnSubmit.visibility = View.GONE
            etZip.visibility = View.GONE

            val retrofitWeather = RetrofitWeather()

            retrofitWeather.getCurrentWeatherService().getCurrentWeather(strSharedZip!!, "3374a45035c536c09672b99863edb72b")
                .enqueue(
                    object : Callback<WeatherObject> {
                        override fun onFailure(call: Call<WeatherObject>, t: Throwable) {
                            Log.d("TAG", t.message)
                        }

                        override fun onResponse(call: Call<WeatherObject>, response: Response<WeatherObject>) {
                            val weatherResponse = response.body()
                            val kelvin = weatherResponse?.main?.temp
                            val celsius: Int? = kelvinToCelsius(kelvin!!).toInt()
                            val farenheit: Int? = celsiusToFahrenheit(celsius!!)
                            tvCurrentTemp.text = farenheit.toString() + "º"
                            if (farenheit!! >= 80) {
                                layout.setBackgroundResource(R.color.color_hot)
                            } else if ((80 > farenheit) && (farenheit >= 60)) {
                                layout.setBackgroundResource(R.color.color_warm)
                            } else if ((60 > farenheit) && (farenheit >= 40)) {
                                layout.setBackgroundResource(R.color.cool_blue)
                            } else {
                                layout.setBackgroundResource(R.color.cold_blue)
                            }
                            if (weatherResponse.clouds!!.all !!<= 35){
                                imgCondition.setImageResource(R.mipmap.ic_sunny_round)
                            } else if((weatherResponse.clouds!!.all !! > 35) && (weatherResponse.clouds!!.all !!<65)){
                                imgCondition.setImageResource(R.drawable.partlycloudy)
                            } else{
                                imgCondition.setImageResource(R.drawable.rainclouds)
                            }
                        }

                    }
                )

            retrofitWeather.getFiveDayForecast().getForecast(strSharedZip, "3374a45035c536c09672b99863edb72b")
                .enqueue(object : Callback<ForecastObject> {
                    override fun onFailure(call: Call<ForecastObject>, t: Throwable) {
                        Log.d("TAG", t.message)
                    }

                    override fun onResponse(call: Call<ForecastObject>, response: Response<ForecastObject>) {
                        val forecastResponse = response.body()
                        tvCity.text = forecastResponse?.city?.name
                        populateRVForecast(forecastResponse!!)
                    }

                })

        } else{
            layout.visibility = View.INVISIBLE
            rvForecast.visibility = View.INVISIBLE
            tvCity.visibility = View.INVISIBLE
            btnSubmit.visibility = View.VISIBLE
            etZip.visibility = View.VISIBLE
        }
    }


    fun celsiusToFahrenheit(celsius: Int): Int {
        return ((celsius * 9.0f / 5.0f) + 32.0f).toInt()
    }

    fun kelvinToCelsius(kelvin: Float): Float {
        return (kelvin - 273.15).toFloat()
    }
    public fun onClick(view: View){
        val strZipCode: String = etZip.text.toString()
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString("zip", strZipCode)
        editor.apply()
        startActivity(Intent(view.context, this::class.java))
    }

    fun populateRVForecast(forecastObject: ForecastObject) {

        val layoutManager = LinearLayoutManager(this)
        val adapter = ForecastRVAdapter(forecastObject.list)
        rvForecast.setLayoutManager(layoutManager)
        rvForecast.setAdapter(adapter)
    }
}
