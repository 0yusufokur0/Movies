package com.resurrection.movies.util

import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.bumptech.glide.load.resource.bitmap.CenterCrop

import com.bumptech.glide.request.RequestOptions


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
            .load(imageUrl)
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
