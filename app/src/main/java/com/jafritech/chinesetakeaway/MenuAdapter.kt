package com.jafritech.chinesetakeaway

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jafritech.chinesetakeaway.helperPackage.Helper


class MenuAdapter(
    // declare context variable and assign MenuActivity context
    private val context: MenuActivity,
    // declare array list of FoodAdapterDataSource
    private val foods: ArrayList<FoodAdapterDataSource>
) : RecyclerView.Adapter<MenuAdapter.ItemHolder>() {

    // override function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(context).inflate(
                // assigning layout menu adapter
                R.layout.menu_layout_adapter, parent, false
            )
        )
    }

    // override function
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        // linking layout field to respective data source
        val foodItem = foods[position]
        holder.name.text = foodItem.name
        holder.price.text = Helper().formatPrice(foodItem.price)
        // creating image resource
        val resourceId = context.resources.getIdentifier(
            "@drawable/" +
                    "ctlogo", "drawable", context.packageName
        )
        holder.img.setImageResource(resourceId)

        // executes when one of the row clicked
        holder.itemView.setOnClickListener {
            // launch ItemActivity with extra information. item name, item price and item image
            val intentItem = Intent(context, ItemActivity::class.java).apply {
                putExtra("NAME", foodItem.name)
                putExtra("PRICE", foodItem.price)
                putExtra("IMG", foodItem.img)
            }
            context.startActivity(intentItem)
        }

    }

    // override function, returns the item count in the item list
    override fun getItemCount(): Int {
        return foods.size
    }

    // local class find view by id to populate list
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.fooditem_layout_tv)
        val price: TextView = view.findViewById(R.id.price_layout_tv)
        val img: ImageView = view.findViewById(R.id.image_layout_img)
    }


}