package com.simpledeeds.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayImage.visibility = View.INVISIBLE
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    var isRunning = false

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            //if light is dim, detecting light
            if (event!!.values[0] < 30 && !isRunning) {
                isRunning = true
                displayImage.visibility = View.VISIBLE
            }else {
                isRunning = false
                displayImage.visibility = View.INVISIBLE
            }
        }catch (e: IOException) {
            print(e)
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}