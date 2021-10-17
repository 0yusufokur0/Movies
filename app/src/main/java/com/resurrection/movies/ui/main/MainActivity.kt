package com.resurrection.movies.ui.main

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.resurrection.movies.R
import com.resurrection.movies.databinding.ActivityMainBinding
import com.resurrection.movies.databinding.ChangeViewDialogBinding
import com.resurrection.movies.databinding.SortDialogBinding
import com.resurrection.movies.ui.base.BaseActivity
import com.resurrection.movies.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationBarView.OnItemSelectedListener {
    var searchView: SearchView? = null
    private var sortAlertDialog: AlertDialog? = null
    private var changeLayoutAlertDialog: AlertDialog? = null
    private var textChangedFun: ((String) -> Unit?)? = null
    lateinit var navController: NavController

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {
        setupBaseComponent()
        binding.toolbarTextView.doOnTextChanged { text, start, count, after ->
            textChangedFun?.let { it1 -> it1(binding.toolbarTextView.text.toString()) }
        }
    }

    private fun setupBaseComponent() {
        binding.toolbar.setBackgroundColor(Color.parseColor("#9E9E9E"))
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        binding.navView.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                navController.navigate(
                    R.id.navigation_home, null, NavOptions.Builder()
                        .setEnterAnim(R.anim.left_to_right_first)
                        .setExitAnim(R.anim.left_to_right_second).build()
                )
            }
            R.id.navigation_favorite -> {
                navController.navigate(
                    R.id.navigation_favorite, null, NavOptions.Builder()
                        .setEnterAnim(R.anim.right_to_left_first)
                        .setExitAnim(R.anim.right_to_left_second).build()
                )
            }
        }
        binding.toolbarTextView.text = null
        this.hideKeyboard(binding.navView)
        return true
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
        /* val mSearchMenuItem = menu.findItem(R.id.action_search)
         mSearchMenuItem.actionView as SearchView*/
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

/*
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
*/

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