package dev.bagasn.invoicmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.bagasn.invoicmaker.model.ItemModel
import dev.bagasn.invoicmaker.utils.TextFormatter
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.row_layout_item_list_result.view.*
import kotlinx.android.synthetic.main.row_table_invoice.view.*
import java.lang.StringBuilder

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val itemList = intent
            .getSerializableExtra("item-list") as ArrayList<*>
        val shipping = intent.getIntExtra("shipping-costs", 0)
        val invoiceSize = intent.getIntExtra("invoice-size", 0)

        var totalDiscount = 0
        var totalValue = 0
        for (i in itemList.indices) {
            val item = itemList[i] as ItemModel
            addItemToView(item)

            totalDiscount += item.discount
            totalValue += (item.value * item.qty) - item.discount
        }

        textTotal.text = "Rp. ${TextFormatter.intToCurrencyText(totalValue)}"
        textDiscount.text = "Rp. ${TextFormatter.intToCurrencyText(totalDiscount)}"

        generateInvoice(totalValue, invoiceSize, shipping)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else if (item.itemId == R.id.actionShare) {
            shareTextInvoice()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("InflateParams")
    private fun addItemToView(item: ItemModel) {
        val view = LayoutInflater.from(applicationContext)
            .inflate(R.layout.row_layout_item_list_result, null, false)

        view.textName.text = item.name

        val desc = "Rp. ${TextFormatter.intToCurrencyText(item.value)}" +
                " (Qty: ${TextFormatter.intToCurrencyText(item.qty)})"
        view.textHarga.text = desc

        layoutItemList.addView(view)
    }

    @SuppressLint("InflateParams")
    private fun generateInvoice(total: Int, invoiceSize: Int, ongkir: Int) {
        val invoice: Double = total.div(invoiceSize.toDouble())
        for (i in 1..invoiceSize) {
            var temp = invoice
            if (i == 1) {
                temp += ongkir
            }

            val view = LayoutInflater.from(applicationContext)
                .inflate(R.layout.row_table_invoice, null, false)

            view.textDesc.text = "Cicialn $i"
            view.textValue.text = "Rp. ${TextFormatter.convertToCurrency(temp)}"

            tableInvoice.addView(view)
        }
    }

    private fun shareTextInvoice() {
        val itemList = intent
        .getSerializableExtra("item-list") as ArrayList<*>
        val shipping = intent.getIntExtra("shipping-costs", 0)
        val invoiceSize = intent.getIntExtra("invoice-size", 0)
        
        val textBuilder = StringBuilder()

        var totalDiscount = 0
        var totalValue = 0
        for (i in itemList.indices) {
            val item = itemList[i] as ItemModel

            totalDiscount += item.discount
            totalValue += (item.value * item.qty) - item.discount

            textBuilder.append("${item.name}\n")
                .append("  Rp. ${TextFormatter.intToCurrencyText(item.value)}")
                .append(" (Qty: ${TextFormatter.intToCurrencyText(item.qty)})\n")
        }

        textBuilder.append("\n")
            .append("Total: Rp. ${TextFormatter.intToCurrencyText(totalValue)}\n")
            .append("Diskon: Rp. ${TextFormatter.intToCurrencyText(totalDiscount)}\n")
            .append("\n")

        val invoice: Double = totalValue.div(invoiceSize.toDouble())
        for (i in 1..invoiceSize) {
            var temp = invoice
            if (i == 1) {
                temp += shipping
            }

            textBuilder.append("Cicilan $i:")
                .append(" Rp. ${TextFormatter.convertToCurrency(temp)}\n")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textBuilder.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share invoice to...")
        startActivity(shareIntent)
    }

}