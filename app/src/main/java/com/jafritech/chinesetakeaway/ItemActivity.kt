package com.jafritech.chinesetakeaway

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jafritech.chinesetakeaway.databinding.ActivityItemBinding
import com.jafritech.chinesetakeaway.helperPackage.Helper

class ItemActivity : AppCompatActivity() {

    // declare sharedPreferences variable
    private lateinit var mSharedPreferences: SharedPreferences
    // declare sqlHandler object

    private lateinit var helper: Helper

    // declare binding variable
    private lateinit var binding: ActivityItemBinding

    // declare variable to track order item quantity
    private var itemPc = 1

    // declare variable to track total order price
    private var totalItemPrice: Double = 0.0

    // declare variable to receive bundle information item name
    var name: String? = ""

    // declare variable to receive bundle information item price
    var price: Double = 0.0

    // declare variable to receive bundle information item image
    private var img: String? = ""

    // declare context
    private lateinit var context: Context

    // starting point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize binding and populate activity
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize SharedPreference variable
        mSharedPreferences = this.getSharedPreferences("orderList", Context.MODE_PRIVATE)

        // initialise helper
        helper = Helper()
        // initialise context
        context = this

        // if the is value in saveInstance
        if (savedInstanceState != null) {
            // assign the respective value to the variables
            itemPc = savedInstanceState.getInt("PC")
            price = savedInstanceState.getDouble("PRICE")
            img = savedInstanceState.getString("IMG")
            name = savedInstanceState.getString("NAME")

        } else {
            // if empty call extractBundle local function to get value from Bundle
            extractBundle()
        }

        // calling local function
        initializeScreenDisplay()

        // executes when minus button clicked
        binding.tvMinus.setOnClickListener {
            // calling local function
            removeItem()
        }

        // Executes when plus button is clicked
        binding.tvPlus.setOnClickListener {
            // calling local function
            addItem()
        }

        // Executes when cancel button is clicked
        binding.cancelItemBtn.setOnClickListener {
            // terminate the current activity
            finish()
        }

        // Executes when add button is clicked
        binding.addItemBtn.setOnClickListener {
            // calling local function
            addItemToBasket()
        }
    }

    // saving the values to retain it if screen reload due to device rotation
    override fun onSaveInstanceState(ActivityInstanceState: Bundle) {
        super.onSaveInstanceState(ActivityInstanceState)
        ActivityInstanceState.putDouble("PRICE", price)
        ActivityInstanceState.putInt("PC", itemPc)
        ActivityInstanceState.putString("IMG", img)
        ActivityInstanceState.putString("NAME", binding.tvItemname.text.toString())
    }

    // local function
    private fun displayDescription() {
        // calculate total price
        totalItemPrice = (itemPc * price)
        // declare description variable and populate value
        val description =
            itemPc.toString() + " Pc(s) of " + name + " = " + Helper().formatPrice(totalItemPrice)
        // populate description value on the screen
        binding.tvTotalPrice.text = description
        // populate item quantity on the screen
        binding.tvQty.text = itemPc.toString()

    }

    // local function to increase item quantity by one
    private fun addItem() {
        if (itemPc < 50) {
            itemPc += 1
            displayDescription()
        } else {
            Toast.makeText(this, "Quantity can not be more than 50", Toast.LENGTH_SHORT).show()
        }
    }

    // local function to decrease item quantity by one
    private fun removeItem() {
        if (itemPc > 1) {
            itemPc -= 1
            displayDescription()
        } else {
            Toast.makeText(this, "Quantity can not be less than 1", Toast.LENGTH_SHORT).show()
        }
    }

    // local function to extract the bundle information
    private fun extractBundle() {
        //declare bundle variable
        val bundle: Bundle? = intent.extras
        // extract information associated with "NAME" key
        name = (bundle ?: return).getString("NAME")
        // extract information associated with "PRICE" key
        price = bundle.getDouble("PRICE")
        // extract information associated with "IMG" key
        img = bundle.getString("IMG")
    }

    // local function to populate screen based on current values
    @SuppressLint("SetTextI18n")
    private fun initializeScreenDisplay() {
        // populate item name
        binding.tvItemname.text = name
        // populate item price
        binding.tvItemPrice.text = Helper().formatPrice(price) + " /pc"
        // prepare item image resources
        val resourceId = this.resources.getIdentifier(
            "@drawable/" +
                    img, "drawable", this.packageName
        )
        // populate item image
        binding.imageView.setImageResource(resourceId)

        // calling local function to populate description
        displayDescription()
    }

    // local function to add order item to order list
    private fun addItemToBasket() {
        // holding item price as a string
        val strPrice: String = (itemPc.toDouble() * price).toString()
        // holding quantity as a string
        val strQty: String = itemPc.toString()

        helper.insertOrder(name ?: return, strQty, strPrice, context)
        // display user notification
        Toast.makeText(
            applicationContext, resources.getString(R.string.item_added_notification),
            Toast.LENGTH_SHORT
        ).show()
        // close current activity
        finish()
    }

}