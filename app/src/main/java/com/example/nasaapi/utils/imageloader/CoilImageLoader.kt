package com.example.nasaapi.utils.imageloader

import android.widget.ImageView
import coil.load
import com.example.nasaapi.R

class CoilImageLoader : AppImageLoader {

    override fun loadInto(url: String, container: ImageView) {
        container.load(url) {
            target(
                onSuccess = { result ->
                    container.setImageDrawable(result)
                },
                onStart = {
                    container.setImageResource(R.drawable.ar_loading_animation)
                },
                onError = {
                    container.setImageResource(R.drawable.ic_error_place_holder)
                },
            )
        }
    }
}