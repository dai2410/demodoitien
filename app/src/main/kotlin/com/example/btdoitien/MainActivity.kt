package com.example.btdoitien

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.btdoitien.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "VND" to 23185.0,
        "EUR" to 0.92,
        "JPY" to 150.0,
        "GBP" to 0.78
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)

        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        binding.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun convertCurrency() {
        val amountText = binding.edtAmount.text.toString()
        if (amountText.isEmpty()) {
            // Nên cập nhật cả khi trống để rõ ràng
            binding.tvResult.text = "Kết quả: "
            return
        }


        val amount = amountText.toDoubleOrNull() ?: 0.0
        val fromCurrency = binding.spinnerFrom.selectedItem.toString()
        val toCurrency = binding.spinnerTo.selectedItem.toString()


        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0

        
        val convertedAmount = if (fromRate != 0.0) {
            amount * (toRate / fromRate)
        } else {
            0.0
        }
        binding.tvResult.text = "Kết quả: %.2f %s".format(convertedAmount, toCurrency)
    }
}