package com.example.subhankar29.junoastrography.di

import com.example.subhankar29.junoastrography.model.AdopService
import com.example.subhankar29.junoastrography.viewmodel.AdopViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: AdopService)

    fun inject(viewModel: AdopViewModel)
}