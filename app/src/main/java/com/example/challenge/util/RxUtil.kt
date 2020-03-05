package com.example.challenge.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.backgroundToMain(): Observable<T> {
    return this.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
}