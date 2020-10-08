package dev.bagasn.invoicmaker.utils

class TextFormatter {

    companion object {

        fun intToCurrencyText(value: Int): String {
            if (value <= 0)
                return "0"
            return textToCurrency(value.toString())
        }

        fun textToCurrency(charText: String): String {
            val characters = charText.toCharArray()
            var textBuilder = ""

            var counter = characters.size - 1
            for (i in 1..characters.size) {
                textBuilder = characters[counter--] + textBuilder
                if (i % 3 == 0 && i < characters.size) {
                    textBuilder = ",$textBuilder"
                }
            }

            return textBuilder
        }

    }

}