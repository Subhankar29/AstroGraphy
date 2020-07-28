package com.example.subhankar29.junoastrography.di

import com.example.subhankar29.junoastrography.model.AdopApi
import com.example.subhankar29.junoastrography.model.AdopService
import com.example.subhankar29.junoastrography.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideAdopApi(): AdopApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AdopApi::class.java)
    }

    @Provides
    fun provideAdopServies(): AdopService {
        return AdopService()
    }


}