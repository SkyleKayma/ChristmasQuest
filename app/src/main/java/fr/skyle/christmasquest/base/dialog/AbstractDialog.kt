package fr.skyle.christmasquest.base.dialog

import androidx.appcompat.app.AppCompatDialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class AbstractDialog : AppCompatDialogFragment() {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}