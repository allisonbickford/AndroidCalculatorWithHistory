package com.example.androidcalculatorwithhistory

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.view.Menu;
import android.view.MenuItem
import android.widget.TextView
import com.example.androidcalculatorwithhistory.UnitsConverter.*
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseReference
import org.joda.time.DateTime
import com.google.firebase.database.FirebaseDatabase
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.mode
import org.joda.time.format.ISODateTimeFormat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.androidcalculatorwithhistory.dummy.HistoryContent


class MainActivity : AppCompatActivity() {
    private var isLength = true
    private var fromLenUnits = LengthUnits.Yards
    private var toLenUnits = LengthUnits.Meters
    private var fromVolUnits = VolumeUnits.Gallons
    private var toVolUnits = VolumeUnits.Liters
    private var topRef: DatabaseReference? = null
    var allHistory: ArrayList<HistoryContent.HistoryItem> = ArrayList()

    object ResultCode {
        val SETTINGS_CODE = 1
        val HISTORY_CODE = 2
    }

    private val chEvListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            var entry = dataSnapshot.getValue(HistoryContent.HistoryItem::class.java)
            entry!!._key = dataSnapshot.key.toString()
            allHistory.add(entry)
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            val entry = dataSnapshot.getValue(HistoryContent.HistoryItem::class.java)
            val newHistory = ArrayList<HistoryContent.HistoryItem>()
            for (t in allHistory) {
                if (t._key != dataSnapshot.key) {
                    newHistory.add(t)
                }
            }
            allHistory = newHistory
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    public override fun onResume() {
        super.onResume()
        topRef = FirebaseDatabase.getInstance().getReference("history")
        topRef!!.addChildEventListener (chEvListener)
    }

    public override fun onPause() {
        super.onPause()
        topRef!!.removeEventListener(chEvListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allHistory = ArrayList()

        val calcButton = findViewById<Button>(R.id.calcButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val modeButton = findViewById<Button>(R.id.modeButton)

        // set listeners for buttons
        clearButton.setOnClickListener {
            clearFields()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(fromInput.windowToken, 0)
        }
        calcButton.setOnClickListener {
            calculate()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(fromInput.windowToken, 0)
        }
        modeButton.setOnClickListener {
            changeMode()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(fromInput.windowToken, 0)
        }


        val fromInput = findViewById<EditText>(R.id.fromInput)
        val toInput = findViewById<EditText>(R.id.toInput)
        fromInput.setOnFocusChangeListener { _, _ -> toInput.text.clear() }
        toInput.setOnFocusChangeListener { _, _ -> fromInput.text.clear() }
    }

    private fun clearFields() {
        val fromInput = findViewById<EditText>(R.id.fromInput)
        val toInput = findViewById<EditText>(R.id.toInput)

        fromInput.text.clear()
        toInput.text.clear()
    }


    private fun calculate() {
        val fromInput = findViewById<EditText>(R.id.fromInput)
        val toInput = findViewById<EditText>(R.id.toInput)

        // If both input fields empty - display error message.
        if(fromInput.text.isEmpty() && toInput.text.isEmpty()) {
            AlertDialog.Builder(this).setTitle("Error Message").
                setMessage("Please enter a value to calculate.")
                .setPositiveButton("OK") { dialog, _->
                    //clearFields()
                    dialog.dismiss()
                }.create().show()

        }

        val fieldToRead = if (fromInput.text.isNotEmpty()) fromInput else toInput
        val fieldToPopulate = if (fromInput.text.isEmpty()) fromInput else toInput

        if (isLength) {
            val fromUnits = if (fieldToRead == fromInput) fromLenUnits else toLenUnits
            val toUnits = if (fromUnits == fromLenUnits) toLenUnits else fromLenUnits
            fieldToPopulate.setText(
                convert(fieldToRead.text.toString().toDouble(), fromUnits, toUnits).toString())
        } else {
            val fromUnits = if (fieldToRead == fromInput) fromVolUnits else toVolUnits
            val toUnits = if (fromUnits == fromVolUnits) toVolUnits else fromVolUnits
            fieldToPopulate.setText(
                convert(fieldToRead.text.toString().toDouble(), fromUnits, toUnits).toString())
        }

        // remember the calculation.
        val fmt = ISODateTimeFormat.dateTime()
        val item = HistoryContent.HistoryItem(
            fromInput.text.toString().toDouble(),
            toInput.text.toString().toDouble(),
            findViewById<TextView>(R.id.titleLabel).text.toString(),
            toUnits.text.toString(),
            fromUnits.text.toString(),
            fmt.print(DateTime.now()),
            fmt.print(DateTime.now())
        )
        HistoryContent.addItem(item)
        topRef!!.push().setValue(item)
    }

    private fun changeMode() {
        isLength = !isLength
        val title = findViewById<TextView>(R.id.titleLabel)
        val fromUnits = findViewById<TextView>(R.id.fromUnits)
        val toUnits = findViewById<TextView>(R.id.toUnits)
        findViewById<EditText>(R.id.toInput).text.clear()
        if (isLength) {
            title.text = resources.getText(R.string.lengthTitle)
            fromUnits.text = fromLenUnits.name
            toUnits.text = toLenUnits.name
        } else {
            title.text = resources.getText(R.string.volumeTitle)
            fromUnits.text = fromVolUnits.name
            toUnits.text = toVolUnits.name
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_action -> {
                navigateToSettings()
                true
            }
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivityForResult(intent, ResultCode.HISTORY_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java).apply {
            if (isLength) {
                putExtra("IS_LENGTH", true)
                putExtra("FROM_UNITS", fromLenUnits.name)
                putExtra("TO_UNITS", toLenUnits.name)
            } else {
                putExtra("IS_LENGTH", false)
                putExtra("FROM_UNITS", fromVolUnits.name)
                putExtra("TO_UNITS", toVolUnits.name)
            }
        }
        startActivityForResult(intent, ResultCode.SETTINGS_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ResultCode.SETTINGS_CODE) {
            clearFields()
            if (resultCode == Activity.RESULT_OK) {
                val intentFromUnits = data?.getStringExtra("FROM_UNITS")
                val intentToUnits = data?.getStringExtra("TO_UNITS")
                val fromUnits = findViewById<TextView>(R.id.fromUnits)
                val toUnits = findViewById<TextView>(R.id.toUnits)
                if (isLength) {
                    if (intentFromUnits != null) {
                        val unitString = LengthUnits.valueOf(intentFromUnits)
                        fromLenUnits = unitString
                        fromUnits.text = fromLenUnits.name
                    }

                    if (intentToUnits != null) {
                        val unitString = LengthUnits.valueOf(intentToUnits)
                        toLenUnits = unitString
                        toUnits.text = toLenUnits.name
                    }
                } else {
                    if (intentFromUnits != null) {
                        val unitString = VolumeUnits.valueOf(intentFromUnits)
                        fromVolUnits = unitString
                        fromUnits.text = fromVolUnits.name
                    }

                    if (intentToUnits != null) {
                        val unitString = VolumeUnits.valueOf(intentToUnits)
                        toVolUnits = unitString
                        toUnits.text = toVolUnits.name
                    }
                }
            }
        } else if (requestCode == ResultCode.HISTORY_CODE) {
            if (resultCode != Activity.RESULT_CANCELED) {
                val vals = data?.getStringArrayExtra("item")

                val title = findViewById<TextView>(R.id.titleLabel)
                if (title.text != vals!![2]) {
                    changeMode()
                }
                findViewById<EditText>(R.id.fromInput).setText(vals[0])
                findViewById<EditText>(R.id.toInput).setText(vals[1])

                findViewById<TextView>(R.id.fromLabel).text = vals[3]
                findViewById<TextView>(R.id.toLabel).text = vals[4]
            }
        }
    }
}
