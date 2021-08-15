package com.jafritech.chinesetakeaway

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.jafritech.chinesetakeaway.databinding.ActivityMainBinding
import com.jafritech.chinesetakeaway.helperPackage.Helper


class MainActivity : AppCompatActivity(), SensorEventListener {

    // declare binding variable
    private lateinit var binding: ActivityMainBinding
//    // declaring sqlHandler object
//    private var sqlHandler: SqlHandler? = null

    // Sensor variable declaration
    private var sensor: Sensor? = null

    //  Sensor manager variable declaration
    private var sensorManager: SensorManager? = null

    // Starting point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initializing binding and populating activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // initialize sensor
        sensor = (sensorManager ?: return).getDefaultSensor(Sensor.TYPE_LIGHT)


        // deleting previous order if left incomplete
        Helper().deleteAllRecords(this)

        // Executes when About button clicked
        binding.aboutMainBtn.setOnClickListener {
            // calling local function
            activityAbout()
        }
        // Executes when Contact button clicked
        binding.contactMainBtn.setOnClickListener {
            // calling local function
            activityContact()
        }
        // Executes when Menu button clicked
        binding.menuMainBtn.setOnClickListener {
            // calling local function
            activityMenu()
        }
        // Executes when Share icon clicked
        binding.shareHomeIv.setOnClickListener {
            // calling local function
            appShare()
        }
    }

    // initializing the option menu xml file
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // execute block of codes based on option selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Executes when Menu option selected
            R.id.action_menu -> {
                // calling local function
                activityMenu()
                true
            }
            // Executes when Contact option selected
            R.id.action_contact -> {
                // calling local function
                activityContact()
                true
            }
            // Executes when About option selected
            R.id.action_about -> {
                // calling local function
                activityAbout()
                true
            }
            // Executes when Close option selected
            else -> {
                // close current activity
                finish()
                true
            }
        }
    }

    // local function to share application link
    private fun appShare() {
        // declare intent
        val sharingIntent = Intent().apply {
            // add intent type
            type = "text/plain"
            // add intent action
            action = Intent.ACTION_SEND
            // prepare message body
            val shareBody = resources.getString(R.string.share_body)
            // add message subject
            putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.chinese_takeaway))
            // add message body
            putExtra(Intent.EXTRA_TEXT, shareBody)
        }
        // declare sharing intent
        val shareIntent = Intent.createChooser(sharingIntent, "share?")
        // start intent
        startActivity(shareIntent)
    }

    // local function to launch About activity
    private fun activityAbout() {
        val intentAbout = Intent(this, AboutActivity::class.java)
        startActivity(intentAbout)
    }

    // local function to launch Contact activity
    private fun activityContact() {
        val intentContact = Intent(this, ContactActivity::class.java)
        startActivity(intentContact)
    }

    // local function to launch Menu activity
    private fun activityMenu() {
        val intentMenu = Intent(this, MenuActivity::class.java)
        startActivity(intentMenu)
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
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

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

}