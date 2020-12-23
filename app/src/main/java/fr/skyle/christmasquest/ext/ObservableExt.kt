package fr.skyle.christmasquest.ext

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.fromIOToMain(): Observable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


