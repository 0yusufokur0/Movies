package com.resurrection.movies.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.resurrection.movies.databinding.SortDialogBinding

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { return true }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { return true }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> { return true }
        }
    }
    toast(context,"No network connection")
    return false
}

 fun toast(context: Context,message:String):Toast{
    var toast:Toast =
        Toast.makeText(context,message,Toast.LENGTH_SHORT)
    toast.show()
    return toast
}
