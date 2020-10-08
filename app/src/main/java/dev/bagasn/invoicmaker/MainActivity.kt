package dev.bagasn.invoicmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import dev.bagasn.invoicmaker.text.CurrencyTextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        textTotalHarga.addTextChangedListener(CurrencyTextWatcher(textTotalHarga))
        textOngkir.addTextChangedListener(CurrencyTextWatcher(textOngkir))
        textBanyakCicilan.addTextChangedListener(CurrencyTextWatcher(textBanyakCicilan))
        textDP.addTextChangedListener(CurrencyTextWatcher(textDP))
        textDiskon.addTextChangedListener(CurrencyTextWatcher(textDiskon))

        buttonGenerate.setOnClickListener {
            val harga = convertViewToInteger(textTotalHarga)
            val ongkir = convertViewToInteger(textOngkir)
            val cicilan = convertViewToInteger(textBanyakCicilan)
            val dp = convertViewToInteger(textDP)
            val diskon = convertViewToInteger(textDiskon)

            if (harga <= 0) {
                showToasMessage("Total harga harus diisi.")
                return@setOnClickListener
            }
            if (cicilan <= 0) {
                showToasMessage("Cicilan harus diisi.")
                return@setOnClickListener
            }
            if (dp <= 0) {
                showToasMessage("DP harus diisi.")
                return@setOnClickListener
            }

            val intent = Intent(applicationContext, ResultActivity::class.java)
            intent.putExtra("harga", harga)
            intent.putExtra("ongkir", ongkir)
            intent.putExtra("cicilan", cicilan)
            intent.putExtra("dp", dp)
            intent.putExtra("diskon", diskon)

            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun convertViewToInteger(view: EditText): Int {
        val text = view.text.toString()
            .replace(",", "")

        if (text.isEmpty())
            return 0

        val value = text.toInt()
        if (value < 0) return 0
        return value
    }

    fun showToasMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
            .show()
    }
}