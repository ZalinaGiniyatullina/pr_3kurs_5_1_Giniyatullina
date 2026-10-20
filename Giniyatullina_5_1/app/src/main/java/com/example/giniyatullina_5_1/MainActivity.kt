package com.example.giniyatullina_5_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val city = findViewById<EditText>(R.id.city)
        val btnGet = findViewById<Button>(R.id.btnGet)
        btnGet.setOnClickListener {
            getResult(city)
        }
    }

    fun getResult(city: EditText) {
        if (city.text.toString().isNotEmpty() && city.text.toString() != null) {
            val key = "38f284cf3c554761c5294f1a430b9c83"
            var url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.text.toString() + "&appid=" + key + "&units=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    val obj = JSONObject(response)

                    val main = obj.getJSONObject("main")
                    val temp = main.getString("temp")

                    Log.d("MyLog", "Response: $temp")

                    val tvResult = findViewById<TextView>(R.id.tvResult)
                    val tvTemp = findViewById<TextView>(R.id.tvTemp)
                    val tvPressure = findViewById<TextView>(R.id.tvPressure)
                    val tvWind = findViewById<TextView>(R.id.tvWind)

                    val name = obj.getString("name")
                    val pressure = main.getString("pressure")
                    val wind = obj.getJSONObject("wind")
                    val speed = wind.getString("speed")

                    tvResult.text = "Город: $name"
                    tvTemp.text = "Температура: $temp °C"
                    tvPressure.text = "Давление: $pressure гПа"
                    tvWind.text = "Ветер: $speed м/с"
                },
                {
                    Log.d("MyLog", "Volley error: ${it}")
                }
            )
            queue.add(stringRequest)
        }
        else
        {
            val sn = Snackbar.make(findViewById(android.R.id.content), "Введите город", Snackbar.LENGTH_LONG)
            sn.setActionTextColor(Color.RED)
            sn.show()
        }
    }

}