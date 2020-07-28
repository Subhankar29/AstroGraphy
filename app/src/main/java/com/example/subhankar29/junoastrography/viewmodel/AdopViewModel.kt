package com.example.subhankar29.junoastrography.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subhankar29.junoastrography.di.DaggerApiComponent
import com.example.subhankar29.junoastrography.model.AdopItems
import com.example.subhankar29.junoastrography.model.AdopService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AdopViewModel: ViewModel() {

    @Inject
    lateinit var adopService: AdopService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val adopData = MutableLiveData<AdopItems>()
    val adopLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun fetchAdopWithDate(date: String) {
        fetchAdop(date)
    }

    fun fetchAdopWithNoDate(){
        fetchAdopNoDate()
    }

    private fun fetchAdop(date: String) {
        loading.value = true
        disposable.add(
            adopService.getAdopWithDate(date)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<AdopItems>() {
                    override fun onSuccess(value: AdopItems?) {
                        adopData.value = value
                        adopLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        adopLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    private fun fetchAdopNoDate() {
        loading.value = true
        disposable.add(
            adopService.getAdopNoDate()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<AdopItems>() {
                    override fun onSuccess(value: AdopItems?) {
                        adopData.value = value
                        adopLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        adopLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}