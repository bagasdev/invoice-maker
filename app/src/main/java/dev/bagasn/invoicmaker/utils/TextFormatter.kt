package dev.bagasn.invoicmaker.utils

import androidx.annotation.NonNull
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class TextFormatter {

    companion object {

        fun intToCurrencyText(@NonNull value: Int): String {
            return convertToCurrency(value.toDouble())
        }

        fun textToCurrency(charText: String): String {
            if (charText.isEmpty())
                return "0"
            return convertToCurrency(charText.toDouble())
        }

        fun convertToCurrency(value: Double): String {
            val formatter = DecimalFormat("#,##0", DecimalFormatSymbols(Locale.US))
            return formatter.format(value)
        }

    }

}