package dev.bagasn.invoicmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.bagasn.invoicmaker.model.ItemModel
import dev.bagasn.invoicmaker.text.CurrencyTextWatcher
import dev.bagasn.invoicmaker.utils.TextFormatter
import kotlinx.android.synthetic.main.activity_calculate_item.*
import kotlinx.android.synthetic.main.row_layout_item_list.view.*

class CalculateItemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_item)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        textOngkir.addTextChangedListener(CurrencyTextWatcher(textOngkir))
        textInvoiceSize.addTextChangedListener(CurrencyTextWatcher(textInvoiceSize))
        buttonGenerate.setOnClickListener { actionGenerateInvoice() }

        progressContentValues()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            finish()
        }
    }

    private fun progressContentValues() {
        val itemList = intent
            .getSerializableExtra("item-list") as ArrayList<*>

        generateItemListView(itemList)
    }

    @SuppressLint("InflateParams")
    private fun generateItemListView(itemList: ArrayList<*>) {
        var mainTotal = 0
        for (i in itemList.indices) {
            val item = itemList[i] as ItemModel

            mainTotal += (item.value * item.qty) - item.discount

            val rowView = LayoutInflater.from(applicationContext)
                .inflate(R.layout.row_layout_item_list, null, false)

            rowView.textName.text = item.name

            val harga = "Rp. ${TextFormatter.intToCurrencyText(item.value)} " +
                    "(Qty: ${TextFormatter.intToCurrencyText(item.qty)})"
            rowView.textHarga.text = harga

            if (item.discount != 0) {
                rowView.layoutDiscount.visibility = View.VISIBLE
                val theFuckingText = "${TextFormatter.intToCurrencyText(item.discount)} %"
                rowView.textDiscount.text = theFuckingText
            }

            layoutItemList.addView(rowView)
        }

        val strTotal = "Rp. ${TextFormatter.intToCurrencyText(mainTotal)}"
        textTotal.text = strTotal
    }

    private fun actionGenerateInvoice() {
        val itemSerializable = intent
            .getSerializableExtra("item-list")

        var ongkir = textOngkir.text.toString()
            .replace(".", "")
            .replace(",", "")

        if (ongkir.isEmpty())
            ongkir = "0"


        val cicilan = textInvoiceSize.text.toString()
            .replace(".", "")
            .replace(",", "")

        if (cicilan.isEmpty() || cicilan == "0") {
            Toast.makeText(applicationContext, "Cicilan belum diisi.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra("item-list", itemSerializable)
        intent.putExtra("shipping-costs", ongkir.toInt())
        intent.putExtra("invoice-size", cicilan.toInt())
        startActivityForResult(intent, 1)
    }

}