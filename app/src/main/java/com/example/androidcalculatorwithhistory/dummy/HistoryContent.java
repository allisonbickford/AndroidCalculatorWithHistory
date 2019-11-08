package com.example.androidcalculatorwithhistory.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HistoryContent {

    public static final List<HistoryItem> ITEMS = new ArrayList<HistoryItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, HistoryItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createHistoryItem(i))
        }
    }

    private fun addItem(item: HistoryItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createHistoryItem(position: Int): HistoryItem {
        return HistoryItem(position.toString(), "Item " + position, makeDetails(position))
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
    data class HistoryItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
