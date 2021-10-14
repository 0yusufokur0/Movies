package com.resurrection.movies.util

import android.app.ActionBar
import android.graphics.Color.green
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.bumptech.glide.load.resource.bitmap.CenterCrop

import com.bumptech.glide.request.RequestOptions
import com.resurrection.movies.R


@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImage(imageUrl: String?) {
/*
    this.load(imageUrl)
*/
    imageUrl?.let {
/*        Glide.with(this)
            .load(imageUrl) // image url
            //.placeholder(R.drawable.placeholder) // any placeholder to load at start
            //.error(R.drawable.imagenotfound)  // any image in case of error
            .override(200, 200) // resizing
            .centerCrop()
            .into(this);  // imageview object
        */

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(25))
        Glide.with(this)
            .load(imageUrl)/*.override(500,750)*/
            .apply(requestOptions)
            .into(this);


    }

}


@BindingAdapter("afterTextChanged")
fun EditText.afterEditTextChanged(onClick: () -> Unit) {
    this.doAfterTextChanged {
        onClick.invoke()
    }
    return
}
