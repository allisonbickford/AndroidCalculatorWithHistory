package com.example.androidcalculatorwithhistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcalculatorwithhistory.dummy.HistoryContent
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity(), HistoryFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: HistoryContent.HistoryItem?) {
        println("Interact!")
        val intent = Intent()
        val vals = arrayOf(
            item?.fromVal.toString(),
            item?.toVal.toString(),
            item?.mode,
            item?.fromUnits,
            item?.toUnits
        )
        intent.putExtra("item", vals)
        setResult(2, intent)    // Should be MainActivity.HISTORY_CODE
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
    }

}
