package com.jafritech.chinesetakeaway

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jafritech.chinesetakeaway.helperPackage.Helper

class OrderAdapter(
    // declare context variable and assign OrderListActivity context
    private val context: OrderListActivity,
    // declare array list of OrderAdapterDataSource
    private val orders: ArrayList<OrderAdapterDataSource>
) : RecyclerView.Adapter<OrderAdapter.ItemHolder>() {

    // override function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(context).inflate(
                // assigning layout menu adapter
                R.layout.order_layout_adapter, parent, false
            )
        )
    }

    // override function
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        // linking layout field to respective data source
        val orderItem = orders[position]
        holder.sno.text = orderItem.itemSno.toString()
        holder.name.text = orderItem.itemName
        holder.qty.text = orderItem.itemQty.toString()
        holder.price.text = Helper().formatPrice(orderItem.itemPrice)
    }

    // override function, returns the item count in the item list
    override fun getItemCount(): Int {
        return orders.size
    }

    // local class find view by id to populate list
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sno: TextView = view.findViewById(R.id.sn_order_adapter_tv)
        val name: TextView = view.findViewById(R.id.item_order_adapter_tv)
        val qty: TextView = view.findViewById(R.id.qty_order_adapter_tv)
        val price: TextView = view.findViewById(R.id.price_order_adapter_tv)
    }
}