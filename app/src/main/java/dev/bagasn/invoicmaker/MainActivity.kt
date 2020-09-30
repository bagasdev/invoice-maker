package dev.bagasn.invoicmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textTotalHarga.addTextChangedListener(CurrencyTextWatcher(textTotalHarga))
    }

    class CurrencyTextWatcher(private val mText: EditText) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            mText.removeTextChangedListener(this)

            var charText = s.toString()

            if (charText.contains(","))
                charText.replace(",", "")
            if (charText.contains("."))
                charText.replace(".", "")

            val resultText = textToCurrency(charText)
            mText.run { setText(resultText) }
            mText.setSelection(resultText.length)

            mText.addTextChangedListener(this)
        }

        override fun afterTextChanged(s: Editable?) {

        }

        fun textToCurrency(charText: String): String {
            val characters = charText.toCharArray()
            val builder = StringBuilder()

            var counter = 0
            for (i in characters.size - 1 downTo 0) {
                builder.append(characters[i])
                if (counter == 2) {
                    builder.append(",")
                    counter = 0
                } else
                    counter++
            }

            return charText.toString()
        }

    }
}