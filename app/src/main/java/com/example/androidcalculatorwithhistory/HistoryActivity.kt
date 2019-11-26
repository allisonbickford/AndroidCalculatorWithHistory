package com.example.androidcalculatorwithhistory

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.androidcalculatorwithhistory.dummy.HistoryContent.HistoryItem

class HistoryActivity : AppCompatActivity(), HistoryFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    override fun onListFragmentInteraction(item: HistoryItem) {
        val intent = Intent()
        val vals: Array<String> = arrayOf(item.fromVal.toString(), item.toVal.toString(), item.mode, item.fromUnits, item.toUnits)
        intent.putExtra("item", vals)
        setResult(MainActivity.ResultCode.HISTORY_CODE,intent)
        finish()

    }

}
