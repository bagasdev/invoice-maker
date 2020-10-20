package dev.bagasn.invoicmaker.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dev.bagasn.invoicmaker.R
import dev.bagasn.invoicmaker.model.ItemModel
import dev.bagasn.invoicmaker.text.CurrencyTextWatcher
import kotlinx.android.synthetic.main.dialog_add_item_barang.*

class AddItemBarangDialog(private val mListener: ItemAddListener? = null) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_item_barang, container, false)
    }

    override fun onResume() {
        super.onResume()

        val dialogWidth = resources.getDimensionPixelOffset(R.dimen.width_dialog_fragment)
        dialog!!.window!!.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        initContent()
    }

    private fun initContent() {
        textHarga.addTextChangedListener(CurrencyTextWatcher(textHarga))
        textQty.addTextChangedListener(CurrencyTextWatcher(textQty))
        textDiscount.addTextChangedListener(CurrencyTextWatcher(textDiscount))

        buttonCancel.setOnClickListener { dismissAllowingStateLoss() }
        buttonTambah.setOnClickListener { actionAddItem() }
    }

    private fun actionAddItem() {
        var errorMessage: String? = null

        val qty = textQty.text.toString()
            .replace(",", "")
            .replace(".", "")
        if (qty.isEmpty())
            errorMessage = "Kuantitas tidak boleh kosong."

        val hargaBarang = textHarga.text.toString()
            .replace(",", "")
            .replace(".", "")
        if (hargaBarang.isEmpty())
            errorMessage = "Harga barang tidak boleh kosong."

        val namaBarang = textNamaBarang.text.toString().trim()
        if (namaBarang.isEmpty())
            errorMessage = "Nama barang harus diisi."

        var discount = textDiscount.text.toString()
            .replace(",", "")
            .replace(".", "")
        if (discount.isEmpty())
            discount = "0"

        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                .show()
            return
        }

        val data = ItemModel(namaBarang, hargaBarang.toInt(), qty.toInt(), discount.toInt())
        mListener?.onAdded(data)

        dismissAllowingStateLoss()
    }

    interface ItemAddListener {
        fun onAdded(data: ItemModel)
    }

}