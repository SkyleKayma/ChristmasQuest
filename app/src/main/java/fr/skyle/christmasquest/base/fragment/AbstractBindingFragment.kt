package fr.skyle.christmasquest.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import fr.skyle.christmasquest.custom.AutoClearedValue
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class AbstractBindingFragment<T : ViewBinding> : Fragment() {

    protected var binding by AutoClearedValue<T>()

    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflate(inflater).also {
            binding = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    abstract fun inflate(inflater: LayoutInflater): T
}