package com.example.androidcalculatorwithhistory

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcalculatorwithhistory.dummy.HistoryContent

import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity(), HistoryFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: HistoryContent.DummyItem?) {
        Snackbar.make(findViewById(android.R.id.content), "You clicked ${item}!", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
    }

}
