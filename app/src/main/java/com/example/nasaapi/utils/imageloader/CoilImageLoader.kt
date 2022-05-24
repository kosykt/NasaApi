package com.example.nasaapi.utils.imageloader

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.nasaapi.R

class CoilImageLoader: AppImageLoader {

    override fun loadInto(url: String, container: ImageView) {
        container.load(url) {
            target(
                onSuccess = { result ->
                    container.setImageDrawable(result)
                },
                onError = {
                    container.setImageResource(R.drawable.ic_error_place_holder)
                },
                onStart = {
                    container.setImageResource(R.drawable.ic_place_holder)
                }
            )
            transformations(
                RoundedCornersTransformation(25f)
            )
        }
    }
}