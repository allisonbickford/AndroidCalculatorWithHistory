package com.example.androidcalculatorwithhistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcalculatorwithhistory.dummy.HistoryContent.HistoryItem

import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity(), HistoryFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: HistoryItem) {
        val intent = Intent()
        val vals: Array<String> = arrayOf(item.fromVal.toString(), item.toVal.toString(), item.mode, item.fromUnits, item.toUnits)
        intent.putExtra("item", vals)
        setResult(MainActivity.ResultCode.HISTORY_CODE,intent)
        finish()

//        Snackbar.make(findViewById(android.R.id.content), "You clicked ${item}!", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
    }

}
