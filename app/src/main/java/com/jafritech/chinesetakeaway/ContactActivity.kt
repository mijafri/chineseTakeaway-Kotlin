package com.jafritech.chinesetakeaway

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.jafritech.chinesetakeaway.databinding.ActivityContactBinding


class ContactActivity : AppCompatActivity(), SensorEventListener {

    // binding variable declaration
    private lateinit var binding: ActivityContactBinding

    // Sensor variable declaration
    private var sensor: Sensor? = null

    //  Sensor manager variable declaration
    private var sensorManager: SensorManager? = null

    // Starting point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding initializing & populating activity
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // initialize sensor
        sensor = (sensorManager ?: return).getDefaultSensor(Sensor.TYPE_LIGHT)

        // Executes when Nav button clicked
        binding.navContactBtn.setOnClickListener {
            // call local function navigate()
            navigate()
        }

        // Executes when Call button is clicked
        binding.callContactBtn.setOnClickListener {
            // call local function call()
            call()
        }

        // Executes when Email button is clicked
        binding.emailContactBtn.setOnClickListener {
            // Check if device is connected to the internet
            if (isConnected()) {
                // if connected, call local function sendEmail()
                sendEmail()
            } else {
                // if not connected, call local function showErrMessage
                showErrMessage()
            }
        }
    }

    // Associate the correct xml menu file on create.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_contact, menu)
        return true
    }

    // Executes when one of the menu options are selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Executes when Back is selected
            R.id.action_contact_back -> {
                // finish the current activity and go back to the activity stack
                finish()
                true
            }
            // Executes when navigate is selected
            R.id.action_contact_nav -> {
                // call local function navigate()
                navigate()
                true
            }
            //Executes when call option selected
            R.id.action_contact_call -> {
                // call local function call()
                call()
                true
            }
            // Executes when Email option is selected
            else -> {
                // call local function email()
                email()
                true
            }
        }
    }

    // local function
    private fun email() {
        // Check if device is connected to internet
        if (isConnected()) {
            // if connected, call local function called sendEmail()
            sendEmail()
        } else {
            // if nof connected, call local function showErrMessage()
            showErrMessage()
        }
    }

    // local function
    private fun navigate() {
        // check if device is connected to internet
        if (isConnected()) {
            // if connected, local function showOnlineMap() called
            showOnlineMap()
        } else {
            // if not connected, local function showErrMessage() called
            showErrMessage()
        }
    }

    // local function
    private fun showOnlineMap() {  // online map method
        // Create location variable
        val strUri =
            resources.getString(R.string.google_location_link) + resources.getString(R.string.conLatitude) + "," + resources.getString(
                R.string.conLongitude
            ) + " (" + resources.getString(R.string.chinese_takeaway) + ")"
        // create new intent
        val intent = Intent().apply {
            // add action
            action = Intent.ACTION_VIEW
            // Parse location variable as Uri and set as data
            data = Uri.parse(strUri)
        }
        // start activity
        startActivity(intent)
    }

    // Local function
    private fun call() {
        // create new intent
        val intentCall = Intent().apply {
            // add action
            action = Intent.ACTION_DIAL
            // parse contact number with Uri parse and assign as data
            data = Uri.parse("tel:" + resources.getString(R.string.contactNo))
        }
        // Start Activity
        startActivity(intentCall)
    }

    // Local function
    private fun sendEmail() {
        // Create new intent
        val intentEmail = Intent().apply {
            // add action
            action = Intent.ACTION_SEND
            // add type
            type = resources.getString(R.string.email_type)
            // put receivers email address
            putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.rest_email)))
            // put subject
            putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.email_subject))
        }
        // Start activity
        startActivity(intentEmail)
    }

    // local function
    private fun isConnected(): Boolean {  // isConnected method
        // declare connection manager variable
        val conMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        // check if the device is connected to internet or nor and return true or false
        return (conMgr.activeNetworkInfo != null && conMgr.activeNetworkInfo!!.isAvailable
                && conMgr.activeNetworkInfo!!.isConnected)
    }

    // Local function
    private fun showErrMessage() {
        // show error message
        Toast.makeText(this, resources.getText(R.string.con_error_msg), Toast.LENGTH_LONG).show()
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


}