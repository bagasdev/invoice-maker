package dev.bagasn.invoicmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bagasn.invoicmaker.adapter.RecyclerItemBarangAdapter
import dev.bagasn.invoicmaker.dialog.AddItemBarangDialog
import dev.bagasn.invoicmaker.model.ItemModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddItemBarangDialog.ItemAddListener {

    private var mRecyclerAdapter: RecyclerItemBarangAdapter? = null
    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRecyclerAdapter = RecyclerItemBarangAdapter(applicationContext)

        recyclerView.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = mRecyclerAdapter

        fabAddItem.hide()
        fabStartCount.hide()

        fabAddItem.setOnClickListener {
            setVisibilityMoreAction()

            AddItemBarangDialog(this)
                .show(supportFragmentManager, "dialog_add_item")
        }
        fabStartCount.setOnClickListener {
            setVisibilityMoreAction()

            actionStartCount()
        }
        fabMoreAction.setOnClickListener { setVisibilityMoreAction() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else if (item.itemId == R.id.actionReset) {
            mRecyclerAdapter?.clearItems()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAdded(data: ItemModel) {
        mRecyclerAdapter?.addItem(data)
    }

    private fun actionStartCount() {
        val itemList = mRecyclerAdapter?.getItemList()
        if (itemList.isNullOrEmpty()) {
            Toast.makeText(
                applicationContext,
                "Belum ada item yang ditambahkan.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val intent = Intent(applicationContext, CalculateItemsActivity::class.java)
        intent.putExtra("item-list", itemList)
        startActivity(intent)
    }

    private fun setVisibilityMoreAction() {
        if (isFabOpen) {
            fabMoreAction.startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.rotate_reverse_90_degree)
            )
            fabStartCount.startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.fade_reverse_top)
            )
            fabAddItem.startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.fade_reverse_top)
            )

            fabAddItem.visibility = View.GONE
            fabStartCount.visibility = View.GONE
        } else {
            fabMoreAction.startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.rotate_90_degree)
            )
            fabStartCount.startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.fade_to_top)
            )
            fabAddItem.startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.fade_to_top)
            )

            fabAddItem.visibility = View.VISIBLE
            fabStartCount.visibility = View.VISIBLE
        }
        isFabOpen = !isFabOpen
    }

}