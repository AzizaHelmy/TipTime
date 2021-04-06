package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButt.setOnClickListener(View.OnClickListener {
            calculateTip()
        })
      //  binding.costEt.setOnClickListener {  view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    //========================================================
    private fun calculateTip() {
        val stringInTextField = binding.costEt.text.toString()
        val coast = stringInTextField.toDoubleOrNull()
        if (coast == null) {
            binding.tipResult.text = ""
            return
        }
        val selectedId = binding.optionsRg.checkedRadioButtonId
        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = coast * tipPercentage
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        val formatedTip=NumberFormat.getCurrencyInstance().format(tip)
        //binding.tipResult.text = formatedTip.toString()//بدل مااحط الرقم وخلاص لا استني
        binding.tipResult.text = getString(R.string.tip_amount, formatedTip)
    }


    //=====================================================
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}