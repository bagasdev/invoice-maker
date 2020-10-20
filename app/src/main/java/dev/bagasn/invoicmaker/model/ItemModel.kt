package dev.bagasn.invoicmaker.model

import java.io.Serializable

data class ItemModel(
    var name: String = "",
    var value: Int = 0,
    var qty: Int = 0,
    var discount: Int = 0
) : Serializable {
    override fun toString(): String {
        return "Nama Barang: $name" +
                "\nHarga Satuan: $value" +
                "\nQty: $qty" +
                if (discount != 0)
                    "\nDiscount: $discount"
                else
                    ""
    }
}