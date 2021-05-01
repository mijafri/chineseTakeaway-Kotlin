package com.jafritech.chinesetakeaway.helperPackage

import java.text.NumberFormat

class Helper {
    fun formatPrice(price: Double) :String {
        val formattedPrice = NumberFormat.getCurrencyInstance().format(price)
        return formattedPrice
    }

//     fun checkOpenClose() {
//        val cal = Calendar.getInstance() // get current time in a Calendar
//        val hour = cal[Calendar.HOUR_OF_DAY]
//        val mints = cal[Calendar.MINUTE]
//        val currentTime = hour * 60 + mints // 1280
//        val openingHours: Int = getResources().getString(R.string.opening_hour_h)
//            .toInt() * 60 + getResources().getString(R.string.opening_hour_m).toInt()
//        val closingHours: Int = getResources().getString(R.string.closing_hour_h)
//            .toInt() * 60 + getResources().getString(R.string.closing_hour_m).toInt()
//         return currentTime > openingHours && currentTime < closingHours
//    }
}