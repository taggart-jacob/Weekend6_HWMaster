package com.example.weekend6_hwmaster

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.weekend6_hwmaster.forecastweatherobject.ForecastObject
import com.example.weekend6_hwmaster.forecastweatherobject.X

class ForecastRVAdapter(val listWeather: List<X>) : RecyclerView.Adapter<ForecastRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRVAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listWeather.size
    }

    override fun onBindViewHolder(holder: ForecastRVAdapter.ViewHolder, position: Int) {
        if (celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) > 81) {
            holder.tvTempMax.setBackgroundResource(R.color.color_hot)
            holder.tvEqualTemp.setBackgroundResource(R.color.color_hot)
        } else if ((celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) <= 81) && ((celsiusToFahrenheit(
                kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) > 70))) {
            holder.tvTempMax.setBackgroundResource(R.color.color_warm)
            holder.tvEqualTemp.setBackgroundResource(R.color.color_warm)
        } else if ((celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) <= 55) && ((celsiusToFahrenheit(
                kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) > 35))) {
            holder.tvTempMax.setBackgroundResource(R.color.cool_blue)
            holder.tvEqualTemp.setBackgroundResource(R.color.cool_blue)
        } else if (celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) <= 35) {
            holder.tvTempMax.setBackgroundResource(R.color.cold_blue)
            holder.tvEqualTemp.setBackgroundResource(R.color.cold_blue)
        } else{
            holder.tvTempMax.setBackgroundResource(R.color.neutral)
            holder.tvEqualTemp.setBackgroundResource(R.color.neutral)
        }

        if (celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) > 81) {
            holder.tvTempMin.setBackgroundResource(R.color.color_hot)
            holder.tvEqualTemp.setBackgroundResource(R.color.color_hot)
        } else if ((celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) <= 81) && ((celsiusToFahrenheit(
                kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) > 70))) {
            holder.tvTempMin.setBackgroundResource(R.color.color_warm)
            holder.tvEqualTemp.setBackgroundResource(R.color.color_warm)
        } else if ((celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) <= 55) && ((celsiusToFahrenheit(
                kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) > 35))) {
            holder.tvTempMin.setBackgroundResource(R.color.cool_blue)
            holder.tvEqualTemp.setBackgroundResource(R.color.cool_blue)
        } else if (celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) <= 35) {
            holder.tvTempMin.setBackgroundResource(R.color.cold_blue)
            holder.tvEqualTemp.setBackgroundResource(R.color.cold_blue)
        } else{
            holder.tvTempMin.setBackgroundResource(R.color.neutral)
            holder.tvEqualTemp.setBackgroundResource(R.color.neutral)
        }

        holder.tvDay.text = "Date/Time: " + listWeather.get(position).dt_txt

        if (celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) == celsiusToFahrenheit(
                kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat()))) {
            holder.tvTempMax.visibility = View.GONE
            holder.tvTempMin.visibility = View.GONE
            holder.tvEqualTemp.text = "Min/Max Temp: " + celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) + "º"
            holder.tvEqualTemp.visibility = View.VISIBLE
        } else {
            holder.tvEqualTemp.visibility = View.GONE
            holder.tvTempMax.visibility = View.VISIBLE
            holder.tvTempMin.visibility = View.VISIBLE
            holder.tvTempMin.text = "Min Temp: " + celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_min.toFloat())) + "º"
            holder.tvTempMax.text = "Max Temp: " + celsiusToFahrenheit(kelvinToCelsius(listWeather.get(position).main.temp_max.toFloat())) + "º"
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        val tvTempMin: TextView = itemView.findViewById(R.id.tvTempMin)
        val tvTempMax: TextView = itemView.findViewById(R.id.tvTempMax)
        val tvEqualTemp: TextView = itemView.findViewById(R.id.tvEqualTemp)
    }

    fun celsiusToFahrenheit(celsius: Int): Int {
        return ((celsius * 9.0f / 5.0f) + 32.0f).toInt()
    }

    fun kelvinToCelsius(kelvin: Float): Int {
        return (kelvin - 273.15).toInt()
    }
}