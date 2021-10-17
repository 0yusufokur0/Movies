package com.resurrection.movies.ui.main

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.resurrection.movies.R
import com.resurrection.movies.databinding.ActivityMainBinding
import com.resurrection.movies.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.ActionBar;
import com.resurrection.movies.databinding.ChangeViewDialogBinding
import com.resurrection.movies.databinding.SortDialogBinding
import com.resurrection.movies.util.LayoutViews

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(){
    var searchView:SearchView? = null
    lateinit var  navController:NavController
    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }
    private var sortAlertDialog: AlertDialog? = null
    private var changeLayoutAlertDialog: AlertDialog? = null
    private var changedText:String = ""
    private var textChangedFun: ((String) -> Unit?)? = null
  lateinit var myActionMenuItem: MenuItem
    lateinit var tempMenuItem:Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Toolbarを取得
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
            toolbar.setBackgroundColor(Color.RED)
        setSupportActionBar(toolbar)
        if (actionBar != null) {
            actionBar?.setDisplayHomeAsUpEnabled(true);
        }
    }


    override fun init(savedInstanceState: Bundle?) {

         navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_favorite))
/*
        setupActionBarWithNavController(navController, appBarConfiguration)
*/
        binding.navView.setupWithNavController(navController)


    }

    fun getAlertDialogs(mSortAlertDialog: AlertDialog?,mChangeLayoutAlertDialog:AlertDialog?) {
        sortAlertDialog = mSortAlertDialog
        changeLayoutAlertDialog = mChangeLayoutAlertDialog
    }

    open fun setSortAlertDialog(recommended:()-> Unit,sortAToZ:()-> Unit, sortZToA:()-> Unit,sortOldToNew:()-> Unit,sortNewToOld:()-> Unit): AlertDialog? {
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

     fun setRecyclerViewLayoutAlertDialog(setGridLayout:() -> Unit,setListLayout:() -> Unit): AlertDialog? {
        val dialogBuilder = AlertDialog.Builder(this)
        val alertBinding: ChangeViewDialogBinding =
            ChangeViewDialogBinding.inflate(LayoutInflater.from(this))
        dialogBuilder.setView(alertBinding.root)

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertBinding.gridViewLayout.setOnClickListener {
            setGridLayout()
/* currentLayoutView = LayoutViews.GRID_LAYOUT
            refresh()*/

        }
        alertBinding.listViewLaout.setOnClickListener {
            setListLayout()
/*
 currentLayoutView = LayoutViews.LIST_LAYOUT
            refresh()
*/

        }
        return alertDialog
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

    @JvmName("getSearchView1")
    fun getSearchView():SearchView?{
        return searchView
    }
/*    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_home -> {
                navController.navigate(R.id.navigation_home)
                searchView?.isIconified = true

            }
            R.id.navigation_favorite -> {
                navController.navigate(R.id.navigation_favorite)
                searchView?.isIconified = true

            }


        }
            searchView?.setIconifiedByDefault(false)
        return true
    }*/


    fun setTextChangedFun(mtextChangedFun: ((String) -> Unit?)?){
        textChangedFun = mtextChangedFun
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val mSearchMenuItem = menu.findItem(R.id.action_search)
        mSearchMenuItem.actionView as SearchView
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (menu != null) {
            tempMenuItem = menu
        }
        menu?.let {
             myActionMenuItem = menu.findItem(R.id.action_search)
            searchView = myActionMenuItem.actionView as SearchView
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) {
/* viewModel.getMovie(newText)
                        searchString = newText*/

                        textChangedFun?.let { it1 -> it1(newText) }

                    }
                    println(newText)
                    return false
                }


            })
        }

        return super.onCreateOptionsMenu(menu)

    }




}