package dev.reece.cobinhood.data

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by reececheng on 2019/1/5.
 */
class CompositeDisposableHelper {
    private val compositeDisposable = CompositeDisposable()

    fun add(d: Disposable) {
        compositeDisposable.add(d)
    }

    fun clear() {
        compositeDisposable.clear()
    }

    fun hasCompositeDisposables(): Boolean {
        return compositeDisposable.size() > 0
    }

    fun remove(d: Disposable) {
        compositeDisposable.remove(d)
    }
}