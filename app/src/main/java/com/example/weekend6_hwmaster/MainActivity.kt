package com.example.weekend6_hwmaster

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekend6_hwmaster.currentweatherobjects.WeatherObject
import com.example.weekend6_hwmaster.forecastweatherobject.ForecastObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var prefs: SharedPreferences? = null
    val PREFS_FILENAME = "com.weekend6_hwmaster.prefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etZip: TextView = findViewById(R.id.etZip)
        val layout: LinearLayout = findViewById(R.id.layoutCurrentTemp)
        val tvCurrentTemp: TextView = findViewById(R.id.tvCurrentTemperature)
        val tvCity: TextView = findViewById(R.id.tvCity)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

        /*if (prefs!=null){
            strSharedZip: String? = this.prefs.getString("zip", "NO ZIP FOUND")
        }*/
        val retrofitWeather = RetrofitWeather()

        retrofitWeather.getCurrentWeatherService().getCurrentWeather("46278", "3374a45035c536c09672b99863edb72b")
            .enqueue(
                object : Callback<WeatherObject> {
                    override fun onFailure(call: Call<WeatherObject>, t: Throwable) {
                        Log.d("TAG", "FUCKED UP")
                    }

                    override fun onResponse(call: Call<WeatherObject>, response: Response<WeatherObject>) {
                        val weatherResponse = response.body()
                        Log.d("TAG", weatherResponse?.main?.temp.toString())
                        val kelvin = weatherResponse?.main?.temp
                        val celsius: Int? = kelvinToCelsius(kelvin!!).toInt()
                        val farenheit: Int? = celsiusToFahrenheit(celsius!!)
                        Log.d("TAG", farenheit.toString())
                        tvCurrentTemp.text = farenheit.toString() + "º"
                        if (farenheit!! >= 80){
                            layout.setBackgroundColor(resources.getColor(R.color.color_hot))
                        } else if ((80 > farenheit) && (farenheit >= 60)){
                            layout.setBackgroundColor(resources.getColor(R.color.color_warm))
                        }
                        else if ((60 > farenheit) && (farenheit >= 40)){
                            layout.setBackgroundColor(resources.getColor(R.color.cool_blue))
                        } else{
                            layout.setBackgroundColor(resources.getColor(R.color.cold_blue))
                        }

                    }

                }
            )

        retrofitWeather.getFiveDayForecast().getForecast("46278", "3374a45035c536c09672b99863edb72b")
            .enqueue(object: Callback<ForecastObject>{
                override fun onFailure(call: Call<ForecastObject>, t: Throwable) {
                    Log.d("TAG", t.message)
                }

                override fun onResponse(call: Call<ForecastObject>, response: Response<ForecastObject>) {
                    val forecastResponse = response.body()
                    Log.d("TAG", forecastResponse?.city.toString())
                    tvCity.text = forecastResponse?.city?.name
                    populateRVForecast(forecastResponse!!)
                }

            })

    }

    fun celsiusToFahrenheit(celsius: Int): Int{
        return (9 / 5 * celsius + 32)
    }

    fun kelvinToCelsius(kelvin: Float): Float {
        return (kelvin - 273.15).toFloat()
    }
    /*fun onClick(strZip: String? = etZip.text.toString()) {
        val editor = prefs?.edit()
        if (strZip == null){
            etZip.setHint("YOU NEED TO ENTER ZIP")
        } else{
            editor?.putString("zip", strZip)
            editor?.apply()
        }
    }*/

    fun populateRVForecast(forecastObject: ForecastObject){

        val layoutManager = LinearLayoutManager(this)
        val adapter = ForecastRVAdapter(forecastObject.list)
        rvForecast.setLayoutManager(layoutManager)
        rvForecast.setAdapter(adapter)
    }
}
