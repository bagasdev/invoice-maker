package dev.bagasn.invoicmaker.text

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import dev.bagasn.invoicmaker.utils.TextFormatter

class CurrencyTextWatcher(private val mText: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mText.removeTextChangedListener(this)

        val charText = s.toString()
            .replace(".", "")
            .replace(",", "")

        val resultText = TextFormatter.textToCurrency(charText)
        mText.run { setText(resultText) }
        mText.setSelection(resultText.length)

        mText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {

    }

}