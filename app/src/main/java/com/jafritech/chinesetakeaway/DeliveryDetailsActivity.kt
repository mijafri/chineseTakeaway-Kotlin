package com.jafritech.chinesetakeaway

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.jafritech.chinesetakeaway.databinding.ActivityDeliveryDetailsBinding
import com.jafritech.chinesetakeaway.helperPackage.Helper

class DeliveryDetailsActivity : AppCompatActivity() {

    // binding variable declaration
    private lateinit var binding: ActivityDeliveryDetailsBinding

    // Location services variable declaration
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // SharedPreferences instance declaration
    private lateinit var prefs: SharedPreferences

    // SharedPreferences editor instance declaration
    private lateinit var editor: SharedPreferences.Editor


    // Starting point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initializing binding and populating activity
        binding = ActivityDeliveryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // calling local function to Populating delivery details if available
        loadDeliveryDetails()
        // initializing Location variable
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Executes when Submit Button click
        binding.submitDdBtn.setOnClickListener {
            // calling submitForm() local function
            submitForm()
        }
        // Executes when Pick Location Button click
        binding.picklocationDdBtn.setOnClickListener {
            // calling getDeviceLocation local function.
            getDeviceLocation()
        }

        // Executes when Clear Button Click
        binding.clearDdBtn.setOnClickListener {
            // calling clearForm() local method
            clearForm()
        }

        // Executes when Cancel Button Click
        binding.cancelDdBtn.setOnClickListener {
            // calling cancel() local method
            cancel()
        }

    }

    // local function to load delivery details if available
    private fun loadDeliveryDetails() {
        // initializing SharedPreferences instance
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE)

        // Check if sharedPreferences name value is not empty
        if (prefs.getString("Name", "") != "") {
            // if not empty
            // assigning values to the respective ExitText
            (binding.nameDdEt as TextView).text = prefs.getString("Name", "")
            (binding.contactnoDdEt as TextView).text = prefs.getString("Contact", "")
            (binding.addLine1DdEt as TextView).text = prefs.getString("HouseNo", "")
            (binding.addLine2DdEt as TextView).text = prefs.getString("PostCode", "")
            (binding.delInstructionDdEt as TextView).text =
                prefs.getString("DeliveryInstruction", "")
            (binding.latDdEt as TextView).text = prefs.getString("Lat", "")
            (binding.longDdEt as TextView).text = prefs.getString("Long", "")
        }
    }

    // Local function to save form data to shared preference
    @SuppressLint("CommitPrefEdits")
    private fun saveFormData() {
        // initializing SharedPreferences instance
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        // initializing SharedPreferences editor instance
        editor = prefs.edit()
        // storing data to the sharedpreferences
        editor.putString("Name", binding.nameDdEt.text.toString())
        editor.putString("Contact", binding.contactnoDdEt.text.toString())
        editor.putString("HouseNo", binding.addLine1DdEt.text.toString())
        editor.putString("PostCode", binding.addLine2DdEt.text.toString())
        editor.putString("DeliveryInstruction", binding.delInstructionDdEt.text.toString())
        editor.putString("Lat", binding.latDdEt.text.toString())
        editor.putString("Long", binding.longDdEt.text.toString())
        // committing change
        editor.apply()
    }

    // Builtin overloaded function
    // binds and populates respective menu option
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_delivery, menu)
        return true
    }

    // Builtin overloaded function
    // Executes the respective codes based on menu option selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // executes when pick location is selected
            R.id.action_menu_delivery_pickLocation -> {
                // calling local function
                getDeviceLocation()
                true
            }
            // executes when pick submit is selected
            R.id.action_menu_delivery_submit -> {
                // local function called
                submitForm()
                true
            }
            // executes when pick clear is selected
            R.id.action_menu_delivery_clear -> {
                // local function called
                clearForm()
                true
            }
            // executes when pick cancel is selected
            else -> {
                // local function called
                cancel()
                true
            }
        }
    }

    // suppressing the Missing Permission
    @SuppressLint("MissingPermission")
    // Local function, picks device current latitude and longitude
    private fun getDeviceLocation() {
        // initializing the fusedLocationClient variable
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // declaring location variable
        val locationRequest = LocationRequest()
        // Assigning  the location accuracy priority
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        // assigning interval 0 as we need the location only once when the button is clicked
        locationRequest.interval = 0
        // assigning numUpdate to 1 as we need value only one when button pick location clicked
        locationRequest.numUpdates = 1
        // Request the location
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper() ?: return
        )
    }

    // Location request callback
    // executes once the location is accessed
    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            // fetching last location based from LocationResult
            val lastLocation: Location = (locationResult ?: return).lastLocation
            // fetching latitude
            val lat = lastLocation.latitude
            // fetching longitude
            val lon = lastLocation.longitude
            // assigning latitude to the TextView on the customer screen
            (binding.latDdEt as TextView).text = lat.toString()
            // assigning longitude to the TextView on the customer screen
            (binding.longDdEt as TextView).text = lon.toString()
        }
    }

    // local function to clear all the field fo delivery form
    // called when clear button is clicked
    private fun clearForm() {
        // assign empty value to all EditText
        (binding.nameDdEt as TextView).text = ""
        (binding.contactnoDdEt as TextView).text = ""
        (binding.addLine1DdEt as TextView).text = ""
        (binding.addLine2DdEt as TextView).text = ""
        (binding.delInstructionDdEt as TextView).text = ""
        (binding.latDdEt as TextView).text = ""
        (binding.longDdEt as TextView).text = ""
    }

    // local function
    // called when cancel button is clicked
    private fun cancel() {
        // finished the DeliveryDetailsActivity and returns back to activity stack
        finish()
    }

    // local function submit form
    // Called when submit button clicked
    private fun submitForm() {
        // calling local function checkAllFieldStatus()
        if (checkAllFieldStatus()) {
            // displaying toast message to customer to fill the form correctly.
            Toast.makeText(
                this,
                resources.getString(R.string.please_fill_form_completely),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // calling local variable saveFormData() to save form data
            saveFormData()
            // calling local method prepareOrderText()
            val address = prepareDelAddress()
            // calling local method prepareOrderHeader()
            val orderHead = prepareOrderHeader()
            // calling local method prepareOrderBody()
            val orderBody = prepareOrderBody()
            // prepare final order
            val order: StringBuilder = java.lang.StringBuilder()
            order.append(orderHead)
            order.append("\n" + orderBody + "\n")
            order.append("\n" + address + "\n")
            order.append("\n----------------------------------------------")

            // creating intent
            val emailInt = Intent().apply {
                // adding intent action
                action = Intent.ACTION_SEND
                // adding intent type
                type = resources.getString(R.string.email_type)
                // adding receiver email
                putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.rest_email)))
                // adding email subject
                putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.email_sub_order))
                // adding order details as emil body
                putExtra(Intent.EXTRA_TEXT, order.toString())
            }
            // start activity
            startActivity(emailInt)
        }
        // call method from helper class to delete all records from order_list table
        Helper().deleteAllRecords(this)
        // close the current activity and move to active activity on activity stack
        finish()
    }


    // local variable checks if form is filled completely
    // and returns true of false accordingly
    private fun checkAllFieldStatus(): Boolean {
        return (binding.nameDdEt.text.toString() == "" ||
                binding.contactnoDdEt.text.toString() == "" ||
                binding.addLine1DdEt.text.toString() == "" ||
                binding.addLine2DdEt.text.toString() == "" ||
                binding.latDdEt.text.toString() == "" ||
                binding.longDdEt.text.toString() == "")
    }

    // Local function, preparing delivery details string from form value and return
    private fun prepareDelAddress(): StringBuilder {
        val custAddress: StringBuilder = java.lang.StringBuilder()
        custAddress.append("Delivery Details \n")
        custAddress.append("-------------------- \n")
        custAddress.append("Name: " + binding.nameDdEt.text.toString() + "\n")
        custAddress.append("Contact: " + binding.contactnoDdEt.text.toString() + "\n")
        custAddress.append("House no: " + binding.addLine1DdEt.text.toString() + "\n")
        custAddress.append("Postcode: " + binding.addLine2DdEt.text.toString() + "\n")
        custAddress.append("Latitude: " + binding.latDdEt.text.toString() + "\n")
        custAddress.append("Longitude: " + binding.longDdEt.text.toString() + "\n\n")
        custAddress.append("Delivery Instruction: " + binding.delInstructionDdEt.text.toString() + "\n")
        // return prepared delivery address
        return custAddress
    }

    // Local function, prepare order header string and return
    private fun prepareOrderHeader(): java.lang.StringBuilder {
        val ordHead: StringBuilder = java.lang.StringBuilder()
        ordHead.append(resources.getString(R.string.app_name) + "\n\n")
        ordHead.append(resources.getString(R.string.order_list_header) + "\n\n")
        // calling function from helper class to arrange gap between header items
        ordHead.append(Helper().arrangeOrderItem("SN.", 3, "Items", 15, "Qty", 4, "Price") + "\n")
        return ordHead
    }

    // Local function to prepare order body and return
    private fun prepareOrderBody(): StringBuilder {
        // declare variable to prepare order body
        val strOrder: StringBuilder = java.lang.StringBuilder()
        // declare variable to count total order item
        var totalQty = 0
        // declare variable to count total order price
        var totalPrice = 0.0
        // check if order list is not empty by calling function from helper class.
        if (Helper().checkOrderList(this)) {
            // if order available
            // get order list by calling method from helper class
            val orders: ArrayList<OrderAdapterDataSource> = Helper().getOrderList(this)
            // loop to read all order items
            for (order in orders) {
                // add one line of order in arranged fashion with the help of function from helper classs
                strOrder.append(
                    Helper().arrangeOrderItem(
                        order.itemSno.toString(),
                        4 - order.itemSno.toString().length,
                        order.itemName,
                        20 - order.itemName.length,
                        order.itemQty.toString(),
                        6 - order.itemQty.toString().length,
                        Helper().formatPrice(order.itemPrice.toString().toDouble())
                    )
                )
                // add new line
                strOrder.append("\n")
                // count total order item quantity
                totalQty += order.itemQty
                // count total order price
                totalPrice += order.itemPrice
            }
            // add total quantity to the order body
            strOrder.append("\nTotal Item(s):  $totalQty \n")
            // add total price to order body
            strOrder.append("Total Bill: " + Helper().formatPrice(totalPrice))
        }
        // return prepared order body
        return strOrder
    }


}