package com.example.subhankar29.junoastrography.model

import com.example.subhankar29.junoastrography.utils.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AdopApi {

    @GET(Constants.URL_WITHOUT_DATE)
    fun getAdopNoDate(): Single<AdopItems>

    @GET(Constants.URL_WITH_DATE)
    fun getAdopWithDate(@Query("date") date: String): Single<AdopItems>
}