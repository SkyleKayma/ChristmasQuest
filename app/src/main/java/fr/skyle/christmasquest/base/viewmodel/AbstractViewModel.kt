package fr.skyle.christmasquest.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.core.KoinComponent

abstract class AbstractViewModel: ViewModel(), KoinComponent {

    protected var disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}