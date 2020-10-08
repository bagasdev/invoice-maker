package dev.bagasn.invoicmaker

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import dev.bagasn.invoicmaker.utils.TextFormatter
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.row_table_invoice.view.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setValue()
    }

    private fun setValue() {
        var hargaBarang = intent.getIntExtra("harga", 0)
        val ongkir = intent.getIntExtra("ongkir", 0)
        val cicilan = intent.getIntExtra("cicilan", 0)
        val dp = intent.getIntExtra("dp", 0)
        val diskon = intent.getIntExtra("diskon", 0)

        if (diskon > 0)
            hargaBarang = hargaBarang.minus(diskon)

        val paymentLeft = hargaBarang.minus(dp)
        val invoice = paymentLeft.div(cicilan)

        textTotalPembayaran.text = TextFormatter.intToCurrencyText(hargaBarang)
        textDiskon.text = TextFormatter.intToCurrencyText(diskon)

        generateInvoiceRow(cicilan, dp, invoice, ongkir)
    }

    private fun generateInvoiceRow(cicilan: Int, dp: Int, invoice: Int, ongkir: Int) {
        if (tableInvoice.childCount > 1)
            tableInvoice.removeViews(1, tableInvoice.childCount)

        for (i in 0..cicilan) {
            val rowView = LayoutInflater.from(applicationContext)
                .inflate(R.layout.row_table_invoice, null, false)

            var payment: String?
            if (i == 0) {
                payment = TextFormatter.intToCurrencyText(dp)
                if (ongkir > 0)
                    payment += " + ${TextFormatter.intToCurrencyText(ongkir)} (ongkir)"
            } else
                payment = TextFormatter.intToCurrencyText(invoice)

            rowView.textDesc.text = "Cicilan ${i + 1}"
            rowView.textValue.text = payment

            tableInvoice.addView(rowView)
        }
    }

}