/*
package com.example.mywidgetkotlins

import android.content.Context
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import java.util.TimeZone



class MySunriseSunsetView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    init {
        // Calculate sunrise and sunset times for a specific location
        val latitude = 37.7749 // Example latitude (adjust to your location)
        val longitude = -122.4194 // Example longitude (adjust to your location)
        val timeZone = TimeZone.getTimeZone("America/Los_Angeles") // Adjust the time zone as needed

        val calendar = Calendar.getInstance()
        val date = calendar.time
        val calculator = SunriseSunsetCalculator(
            com.luckycatlabs.sunrisesunset.dto.Location(latitude, longitude),
            "America/Los_Angeles" // Replace with your desired time zone identifier
        )


         // Now you have the sunrise and sunset times, you can use them in your custom view as needed.
        // sunrise and sunset are Date objects representing the times.
    }
}
*/
