package com.example.androidcalculatorwithhistory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcalculatorwithhistory.UnitsConverter.*
import com.example.androidcalculatorwithhistory.dummy.HistoryContent
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime

class MainActivity : AppCompatActivity() {
    private var isLength = true
    private var fromLenUnits = LengthUnits.Yards
    private var toLenUnits = LengthUnits.Meters
    private var fromVolUnits = VolumeUnits.Gallons
    private var toVolUnits = VolumeUnits.Liters
    private var SETTINGS_CODE = 1
    private var HISTORY_CODE = 2

    private enum class Mode { Length, Volume }

    private var mode: Mode = Mode.Length

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val item: HistoryContent.HistoryItem?


        if (isLength) {
            val fromUnits = if (fieldToRead == fromInput) fromLenUnits else toLenUnits
            val toUnits = if (fromUnits == fromLenUnits) toLenUnits else fromLenUnits
            val dVal = fieldToRead.text.toString().toDouble()
            val cVal = convert(dVal, fromUnits, toUnits)

            fieldToPopulate.setText(
                convert(fieldToRead.text.toString().toDouble(), fromUnits, toUnits).toString())
            item = HistoryContent.HistoryItem(
                dVal, cVal, mode.toString(),
                toUnits.toString(), fromUnits.toString(), DateTime.now()
            )
            HistoryContent.addItem(item)

        } else {
            val fromUnits = if (fieldToRead == fromInput) fromVolUnits else toVolUnits
            val toUnits = if (fromUnits == fromVolUnits) toVolUnits else fromVolUnits
            val dVal = fieldToRead.text.toString().toDouble()
            val cVal = convert(dVal, fromUnits, toUnits)

            fieldToPopulate.setText(
                convert(fieldToRead.text.toString().toDouble(), fromUnits, toUnits).toString())
            item = HistoryContent.HistoryItem(
                dVal, cVal, mode.toString(),
                toUnits.toString(), fromUnits.toString(), DateTime.now()
            )
            HistoryContent.addItem(item)
        }
    }

    private fun changeMode() {
        isLength = !isLength
        val title = findViewById<TextView>(R.id.titleLabel)
        val fromUnits = findViewById<TextView>(R.id.fromUnits)
        val toUnits = findViewById<TextView>(R.id.toUnits)
        findViewById<EditText>(R.id.toInput).text.clear()
        if (isLength) {
            mode = Mode.Length
            title.text = resources.getText(R.string.lengthTitle)
            fromUnits.text = fromLenUnits.name
            toUnits.text = toLenUnits.name
        } else {
            mode = Mode.Volume
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
                startActivityForResult(intent, HISTORY_CODE)
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
        startActivityForResult(intent, SETTINGS_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === SETTINGS_CODE) {
            this.fromUnits.text = data?.getStringExtra("fromUnits")
            this.toUnits.text = data?.getStringExtra("toUnits")
            //updateScreen()
        } else if (resultCode === HISTORY_CODE) {
            val vals = data!!.getStringArrayExtra("item")
            this.fromInput.setText(vals[0])
            this.toInput.setText(vals[1])
            this.mode = Mode.valueOf(vals[2])
            this.fromUnits.text = vals[3]
            this.toUnits.text = vals[4]
            this.titleLabel.text = ("$mode Converter")
        }

    }
}
