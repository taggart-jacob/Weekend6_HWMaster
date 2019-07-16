package com.example.weekend6_hwmaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.weekend6_hwmaster.forecastweatherobject.ForecastObject
import com.example.weekend6_hwmaster.forecastweatherobject.X

class ForecastRVAdapter(val listWeather: List<X>): RecyclerView.Adapter<ForecastRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRVAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listWeather.size
    }

    override fun onBindViewHolder(holder: ForecastRVAdapter.ViewHolder, position: Int) {
        holder.tvDay.text = "Day of Week: " + listWeather.get(position).dt_txt
        holder.tvTempMin.text = "Min Temp: " + celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat()))
        holder.tvTempMax.text = "Max Temp: " + celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat()))
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        val tvTempMin: TextView = itemView.findViewById(R.id.tvTempMin)
        val tvTempMax: TextView = itemView.findViewById(R.id.tvTempMax)
    }

    fun celsiusToFahrenheit(celsius: Int): Int {
        return (9 / 5 * celsius + 32)
    }

    fun kelvinToCelsius(kelvin: Float): Int {
        return (kelvin - 273.15).toInt()
    }
}