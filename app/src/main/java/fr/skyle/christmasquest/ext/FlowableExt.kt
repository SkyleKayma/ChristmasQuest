package fr.skyle.christmasquest.ext

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Flowable<T>.fromIOToMain(): Flowable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


