package dev.bagasn.invoicmaker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.bagasn.invoicmaker.R
import dev.bagasn.invoicmaker.model.ItemModel
import dev.bagasn.invoicmaker.utils.TextFormatter
import kotlinx.android.synthetic.main.item_recycler_barang.view.*

class RecyclerItemBarangAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerItemBarangAdapter.FuckingHolder>() {

    class FuckingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView? = itemView.textNamaBarang
        val textValue: TextView? = itemView.textHarga
        val textQty: TextView? = itemView.textQty
        val btnRemove: Button? = itemView.buttonRemove
        val btnEdit: Button? = itemView.buttonEdit
    }

    private var mItemList = mutableListOf(
        ItemModel()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuckingHolder {
        val view: View

        if (viewType == 1) {
            view = View(mContext)

            val height = mContext.resources
                .getDimensionPixelOffset(R.dimen.height_padding_list_with_floating_button)
            val layoutParams = ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)

            view.layoutParams = layoutParams
        } else
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler_barang, parent, false)

        return FuckingHolder(view)
    }

    override fun onBindViewHolder(holder: FuckingHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            val item = mItemList[position]

            holder.textName?.text = item.name
            holder.textValue?.text = TextFormatter.intToCurrencyText(item.value)
            holder.textQty?.text = TextFormatter.intToCurrencyText(item.qty)

            holder.btnRemove?.setOnClickListener { actionRemove(position) }
            holder.btnEdit?.setOnClickListener { actionEdit() }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mItemList.size - 1) 1 else 0
    }

    override fun getItemCount() = mItemList.size

    fun getItemList(): ArrayList<ItemModel> {
        val datum = ArrayList<ItemModel>()
        datum.addAll(mItemList)
        datum.removeAt(datum.lastIndex)
        return datum
    }

    fun addItem(item: ItemModel) {
        val index = mItemList.lastIndex
        if (index != -1)
            mItemList.add(index, item)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mItemList.clear()
        mItemList.add(ItemModel())
        notifyDataSetChanged()
    }

    private fun actionRemove(pos: Int) {
        mItemList.removeAt(pos)
        notifyDataSetChanged()
    }

    private fun actionEdit() {
        Toast.makeText(mContext, "Feature is coming soon.", Toast.LENGTH_SHORT)
            .show()
    }
}