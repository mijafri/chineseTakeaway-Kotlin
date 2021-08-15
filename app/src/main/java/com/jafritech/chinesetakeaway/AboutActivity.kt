package com.jafritech.chinesetakeaway

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

// About restaurant
class AboutActivity : AppCompatActivity(), SensorEventListener {

    // Sensor variable declaration
    private var sensor: Sensor? = null

    //  Sensor manager variable declaration
    private var sensorManager: SensorManager? = null

    // Starting point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // initialize sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // initialize sensor
        sensor = (sensorManager ?: return).getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    // Associate xml menu file on create
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    // executes when the menu options selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about_back -> {
                // when back selected
                finish()
                true
            }
            else -> {
                true
            }
        }
    }

    // Ambient light sensor start
    // override onSensorChanged
    override fun onSensorChanged(event: SensorEvent?) {
        // checking if the light value is less than 30
        if ((event ?: return).values[0] <= 30) {
            // if value less than or equal to 30, activate night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // if value greater than 30, deactivate night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    // override onAccuracyChanged
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // override onResume. Register sensor manager
    override fun onResume() {
        super.onResume()
        (sensorManager ?: return).registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // override onPause. unregister sensor manager
    override fun onPause() {
        super.onPause()
        (sensorManager ?: return).unregisterListener(this)
    }
    // Ambient light sensor end

    // image source : https://unsplash.com/photos/N_Y88TWmGwA
}