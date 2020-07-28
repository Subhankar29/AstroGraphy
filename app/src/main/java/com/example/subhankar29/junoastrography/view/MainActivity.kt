package com.example.subhankar29.junoastrography.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.subhankar29.junoastrography.R
import com.example.subhankar29.junoastrography.model.AdopItems
import com.example.subhankar29.junoastrography.utils.*
import com.example.subhankar29.junoastrography.viewmodel.AdopViewModel
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifImageView
import java.util.*


class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, View.OnClickListener {


    lateinit var progressbar: GifImageView
    lateinit var mediaType: String
    lateinit var videoUrl: String
    lateinit var viewModel: AdopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(AdopViewModel::class.java)
        progressbar = findViewById(R.id.progressBar)
        datePicker.setOnClickListener(this)
        zoomPlayButton.setOnClickListener(this)

        if(isInternetConnected(this)) {
            viewModel.fetchAdopWithNoDate()
            observeViewModel()
        }
        else{
            displayErrorMessage(resources.getText(R.string.not_connected_internet).toString())
        }
    }

    fun observeViewModel() {
        viewModel.adopData.observe(this, Observer { adopData ->
            adopData?.let{
                initView(it)
            }
        })

        viewModel.adopLoadError.observe(this, Observer { isError ->
            isError?.let {
                if(it){
                    displayErrorMessage(resources.getText(R.string.error_message).toString())
                }else{
                    zoomPlayButton.visibility = View.VISIBLE
                    errorText.visibility = View.INVISIBLE
                }
            }
        })


    }

    private fun initView(adopItem: AdopItems) {
        tvTitle.text = adopItem.title
        tvDescription.text = adopItem.explanation
        videoUrl = adopItem.url.toString()

        if(adopItem.media_type == Constants.MEDIA_TYPE_IMAGE){

            mediaType = Constants.MEDIA_TYPE_IMAGE
            imageView.loadImage(adopItem.hdurl, progressbar)
            zoomPlayButton.setImageResource(R.drawable.ic_baseline_search_24)

        }else{

            mediaType = Constants.MEDIA_TYPE_VIDEO
            imageView.loadImage(adopItem.url?.let { it1 ->
                getYoutubeVideoId(it1)?.let { it1 ->
                    getVideoThumbNailUrl(
                        it1
                    )
                }
            }, progressbar)
            zoomPlayButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }

    }


    private fun getDate() {
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePickerDialog =
            DatePickerDialog(this@MainActivity, this@MainActivity, year, month,day)
        datePickerDialog.show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val date = "$year-$month-$dayOfMonth"
        viewModel.fetchAdopWithDate(date)
    }

    override fun onClick(view: View?) {

        if(isInternetConnected(this)){
            when(view?.id){
                R.id.datePicker -> {
                    getDate()
                }

                R.id.zoomPlayButton -> {
                    if(mediaType == Constants.MEDIA_TYPE_IMAGE){
                        viewFullScreen()
                    }else{
                        playVideo()
                    }
                }
            }
        }else{
            displayErrorMessage(resources.getText(R.string.not_connected_internet).toString() )
        }

    }

    private fun playVideo() {
        val intent = YouTubeStandalonePlayer.createVideoIntent(
            this,
            Constants.YOUTUBE_DEVELOPER_KEY,
            getYoutubeVideoId(videoUrl)
        )
        startActivity(intent)
    }

    private fun viewFullScreen() {

        if (tvTitle.visibility == View.VISIBLE){
            tvTitle.visibility = View.INVISIBLE
            datePicker.visibility = View.INVISIBLE
            tvDescription.visibility = View.INVISIBLE
        }else{
            tvTitle.visibility = View.VISIBLE
            datePicker.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
        }
    }

    private fun displayErrorMessage(errorMessage: String) {
        zoomPlayButton.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE
        errorText.text = errorMessage
        errorText.visibility = View.VISIBLE
        imageView.setImageDrawable(null)
    }
}


