package com.resurrection.movies.ui.main

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.resurrection.movies.R
import com.resurrection.movies.databinding.ActivityMainBinding
import com.resurrection.movies.databinding.ChangeViewDialogBinding
import com.resurrection.movies.databinding.SortDialogBinding
import com.resurrection.movies.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    var searchView: SearchView? = null
    private var sortAlertDialog: AlertDialog? = null
    private var changeLayoutAlertDialog: AlertDialog? = null
    private var textChangedFun: ((String) -> Unit?)? = null


    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) { setupBaseComponent() }

    private fun setupBaseComponent() {
        binding.toolbar.setBackgroundColor(Color.RED)
        setSupportActionBar(findViewById(R.id.toolbar))
        var navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

    fun getAlertDialogs(mSortAlertDialog: AlertDialog?, mChangeLayoutAlertDialog: AlertDialog?) {
        sortAlertDialog = mSortAlertDialog
        changeLayoutAlertDialog = mChangeLayoutAlertDialog
    }

    fun setSortAlertDialog(
        recommended: () -> Unit,
        sortAToZ: () -> Unit,
        sortZToA: () -> Unit,
        sortOldToNew: () -> Unit,
        sortNewToOld: () -> Unit
    ): AlertDialog? {
        val dialogBuilder = AlertDialog.Builder(this)
        val alertBinding: SortDialogBinding =
            SortDialogBinding.inflate(LayoutInflater.from(this))
        dialogBuilder.setView(alertBinding.root)

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertBinding.recommended.setOnClickListener { recommended() }
        alertBinding.sortAToZ.setOnClickListener { sortAToZ() }
        alertBinding.sortZtoA.setOnClickListener { sortZToA() }
        alertBinding.sortOldToNew.setOnClickListener { sortOldToNew() }
        alertBinding.sortNewToOld.setOnClickListener { sortNewToOld() }
        return alertDialog
    }

    fun setRecyclerViewLayoutAlertDialog(
        setGridLayout: () -> Unit,
        setListLayout: () -> Unit
    ): AlertDialog? {
        val dialogBuilder = AlertDialog.Builder(this)
        val alertBinding: ChangeViewDialogBinding =
            ChangeViewDialogBinding.inflate(LayoutInflater.from(this))
        dialogBuilder.setView(alertBinding.root)

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertBinding.gridViewLayout.setOnClickListener { setGridLayout() }
        alertBinding.listViewLaout.setOnClickListener { setListLayout() }
        return alertDialog
    }


    @JvmName("getSearchView1")
    fun getSearchView(): SearchView? {
        return searchView
    }

    fun setTextChangedFun(mtextChangedFun: ((String) -> Unit?)?) {
        textChangedFun = mtextChangedFun
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val mSearchMenuItem = menu.findItem(R.id.action_search)
        mSearchMenuItem.actionView as SearchView
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        menu?.let {
            var myActionMenuItem = menu.findItem(R.id.action_search)
            searchView = myActionMenuItem.actionView as SearchView
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) textChangedFun?.let { it1 -> it1(newText) }
                    return false
                }

            })
        }

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort -> sortAlertDialog?.show()
            R.id.action_change_layout -> {
                changeLayoutAlertDialog?.show()
            }

        }
        return super.onOptionsItemSelected(item)

    }


}