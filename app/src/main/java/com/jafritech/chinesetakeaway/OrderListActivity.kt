package com.jafritech.chinesetakeaway

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jafritech.chinesetakeaway.databinding.ActivityOrderListBinding

class OrderListActivity : AppCompatActivity() {
    // binding declaration
    private lateinit var binding: ActivityOrderListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding implementation
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // resourcing the order list from orderAdapterDataSource
        val orderList = OrderAdapterDataSource.getOrderList(this)

        // biding RecyclerView with LinearLayout
        binding.orderlistRv.layoutManager = LinearLayoutManager(this)
        // binding RecyclerView with Order Adapter and passing the order list
        binding.orderlistRv.adapter = OrderAdapter(this, orderList)

        // on Menu button click. closing the current activity and going
        // back to stack MenuActivity
        binding.menuOrderlistBtn.setOnClickListener {
            // calling builtin finish() function
            finish()
        }

        // On Check Out button click
        binding.checkoutOrderlistBtn.setOnClickListener {
            // calling local checkOut() function
            checkOut()
        }

    }

    // Local function
    private fun checkOut() {
        val intentCheckout = Intent(this, DeliveryDetailsActivity::class.java)
        startActivity(intentCheckout)
        finish()
    }

    // Builtin overloaded function
    // bind and populate respective menu xml file
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_orderlist, menu)
        return true
    }

    // Builtin overloaded function
    // executes the respective code based on selected menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // on Check Out menu option selection
            R.id.action_menu_orderList_checkOut -> {
                checkOut()
                true
            }
            else -> {
                // on Menu menu option selected
                finish()
                true
            }
        }
    }


}