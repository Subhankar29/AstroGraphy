package com.example.subhankar29.junoastrography.model

import com.example.subhankar29.junoastrography.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class AdopService {

    @Inject
    lateinit var api: AdopApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getAdopNoDate(): Single<AdopItems> {
        return api.getAdopNoDate()
    }

    fun getAdopWithDate(date: String): Single<AdopItems> {
        return api.getAdopWithDate(date)
    }

}