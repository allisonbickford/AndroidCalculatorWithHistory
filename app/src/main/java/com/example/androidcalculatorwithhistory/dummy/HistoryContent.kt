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
        addItem(HistoryItem(2.0, 1.829, "Length", "Yards", "Meters", now.minusDays(1)))
        addItem(HistoryItem(1.0, 3.785, "Volume", "Gallons", "Liters", now.minusDays(1)))
        addItem(HistoryItem(2.0, 1.829, "Length", "Yards", "Meters", now.plusDays(1)))
        addItem(HistoryItem(1.0, 3.785, "Volume", "Gallons", "Liters", now.plusDays(1)))
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
        val fromVal: Double?, val toVal: Double?, val mode: String,
        val fromUnits: String, val toUnits: String, val timestamp: DateTime
    ) {

        override fun toString(): String {
            return this.fromVal.toString() + " " + this.fromUnits + " = " + this.toVal + " " + this.toUnits
        }
    }

}
