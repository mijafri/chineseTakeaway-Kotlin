package com.jafritech.chinesetakeaway

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class FoodAdapterDataSource(
    val itemId: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val img: String = ""
) : Serializable {

    // companion object
    companion object {
        // local function to get order list
        fun getFoods(filename: String, context: Context): ArrayList<FoodAdapterDataSource> {
            val foodItemList = ArrayList<FoodAdapterDataSource>()

            try {
                // open received file name file from asset
                val inputStream = context.assets.open(filename)
                // declare input stream buffer
                val buffer = ByteArray(inputStream.available())
                // read the buffer
                inputStream.read(buffer)
                // close the buffer
                inputStream.close()

                // declare Json variable
                val json = JSONObject(String(buffer, Charsets.UTF_8))
                // get contents from foodmenu file from assets folder
                val foodmenu = json.getJSONArray("foodmenu")

                // loop to read the content
                for (i in 0 until foodmenu.length()) {
                    // add each row to foodItem array list
                    foodItemList.add(
                        FoodAdapterDataSource(
                            foodmenu.getJSONObject(i).getInt("itemId"),
                            foodmenu.getJSONObject(i).getString("name"),
                            foodmenu.getJSONObject(i).getDouble("price"),
                            foodmenu.getJSONObject(i).getString("img")
                        )
                    )
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            // return food item list
            return foodItemList
        }
    }

}

