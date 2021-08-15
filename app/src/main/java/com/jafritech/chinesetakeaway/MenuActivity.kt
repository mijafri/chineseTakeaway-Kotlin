package com.jafritech.chinesetakeaway

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jafritech.chinesetakeaway.databinding.ActivityMenuBinding
import com.jafritech.chinesetakeaway.helperPackage.Helper

class MenuActivity : AppCompatActivity() {

    // declare binding variable
    private lateinit var binding: ActivityMenuBinding


    // starting point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initializing binding and populating activity
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fetching item menu list from foodmenu.json file
        val foodItems = FoodAdapterDataSource.getFoods("foodmenu.json", this)

        // assigning recycler view as linear layout manager
        binding.menulistMenuRv.layoutManager = LinearLayoutManager(this)
        // populating recycler view with MenuAdapter having menu.
        binding.menulistMenuRv.adapter = MenuAdapter(this, foodItems)

        // Executes when order list button clicked
        binding.orderlistMenuBtn.setOnClickListener {
            // calling local function
            orderList()

        }
        // Executes when checkout button clicked
        binding.checkoutMenuBtn.setOnClickListener {
            // calling local function
            checkout()
        }

    }

    // local function
    private fun orderList() {
        // check if order list not empty
        if (Helper().checkOrderList(this)) {
            // if not empty, launch order list activity
            val intentOL = Intent(this, OrderListActivity::class.java)
            startActivity(intentOL)
        } else {
            // if empty, show user message
            Toast.makeText(
                this,
                resources.getString(R.string.empty_order_list_message),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    // initialize xml menu file
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    // executes when one of the menu option selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Executes when checkout option selected
            R.id.action_menu_menu_checkOut -> {
                // calling local function
                checkout()
                true
            }
            // Executes when order list option selected
            R.id.action_menu_menu_orderList -> {
                // calling local function
                orderList()
                true
            }
            // Executes when back option selected
            else -> {
                // close current activity
                finish()
                true
            }
        }
    }

    // local function
    private fun checkout() {
        // check if order list is not empty
        if (Helper().checkOrderList(this)) {
            // if order list not empty, launch deliveryDetailsActivity
            val intentCheckout = Intent(this, DeliveryDetailsActivity::class.java)
            startActivity(intentCheckout)
            // close current activity
            finish()
        } else {
            // if order list is empty, display user notification
            Toast.makeText(
                this,
                resources.getString(R.string.empty_order_list_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

