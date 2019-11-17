package com.example.androidcalculatorwithhistory.dummy

import java.util.ArrayList
import org.joda.time.DateTime


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object HistoryContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<HistoryItem> = ArrayList()

    init {
        val now: DateTime = DateTime.now()
        addItem(HistoryItem(2.0, 1.829, "Length", "Yards", "Meters", now.minusDays(1).toString()))
        addItem(HistoryItem(1.0, 3.785, "Volume", "Gallons", "Liters", now.minusDays(1).toString()))
        addItem(HistoryItem(2.0, 1.829, "Length", "Yards", "Meters", now.plusDays(1).toString()))
        addItem(HistoryItem(1.0, 3.785, "Volume", "Gallons", "Liters", now.plusDays(1).toString()))
    }


    public fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    class HistoryItem(
        val fromVal: Double? = 0.0,
        val toVal: Double? = 0.0,
        val mode: String = "Length",
        val fromUnits: String = "Miles",
        val toUnits: String = "Meters",
        val timestamp: String = DateTime.now().toString(),
        var _key: String = "None"
    ) {


        override fun toString(): String {
            return this.fromVal.toString() + " " + this.fromUnits + " = " + this.toVal + " " + this.toUnits
        }
    }

}
