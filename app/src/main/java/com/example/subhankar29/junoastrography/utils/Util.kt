@file:Suppress("DEPRECATION")

package com.example.subhankar29.junoastrography.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.subhankar29.junoastrography.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import pl.droidsonroids.gif.GifImageView
import java.util.regex.Matcher
import java.util.regex.Pattern


fun getYoutubeVideoId(youtubeUrl: String): String? {

    var videoId: String? = null
    val pattern ="(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"

    val compiledPattern: Pattern = Pattern.compile(pattern)

    val matcher: Matcher = compiledPattern.matcher(youtubeUrl)

    if(matcher.find()){
        videoId= matcher.group()
    }
    return videoId
}

fun getVideoThumbNailUrl(videoId: String): String?{

    return "https://img.youtube.com/vi/$videoId/0.jpg"
}


fun ImageView.loadImage(uri: String?, progressBar: GifImageView) {

    progressBar.visibility = View.VISIBLE

   Picasso
       .get()
       .load(uri)
       .error(R.drawable.error)
       .fit()
       .into(this, object: Callback {
           override fun onSuccess() {
               progressBar.visibility = View.INVISIBLE

           }
           override fun onError(e: java.lang.Exception?) {

           }
       })
}

fun isInternetConnected(context: Context): Boolean {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

    return activeNetwork?.isConnectedOrConnecting == true
}





