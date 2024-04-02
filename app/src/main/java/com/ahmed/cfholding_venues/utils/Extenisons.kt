package com.ahmed.cfholding_venues.utils

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ahmed.cfholding_venues.R
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


fun String?.alternate(alt: String = Constants.General.DASH_TEXT): String {
    return if (!this?.trim().isNullOrEmpty()) this?.trim().toString() else alt
}

fun ImageView.setNetworkImage(imageUrl: String?){
    Glide
        .with(this.context)
        .load(imageUrl)
        .fitCenter()
        .placeholder(R.drawable.ic_downloading)
        .error(R.drawable.ic_error)
        .into(this)
}

fun <T : Any?, L : SharedFlow<T>> LifecycleOwner.observe(sharedFlow: L, body: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            sharedFlow.collect {
                body.invoke(it)
            }
        }
    }
}