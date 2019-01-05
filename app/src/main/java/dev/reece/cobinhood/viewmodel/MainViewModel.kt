package dev.reece.cobinhood.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import dev.reece.cobinhood.databinding.ActivityMainBinding
import dev.reece.cobinhood.model.ApiService
import dev.reece.cobinhood.model.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by reececheng on 2019/1/5.
 */
class MainViewModel constructor(application: Application, private var activityBinding: ActivityMainBinding) : AndroidViewModel(application) {

    companion object {
        val RECYCLER_VIEW_TYPE_LIST = 1
        val RECYCLER_VIEW_TYPE_GRID = 2
    }

    private val mutableTickers : MutableLiveData<ArrayList<Model.Ticker>> = MutableLiveData()
    val tickers : LiveData<ArrayList<Model.Ticker>> = mutableTickers

    private val mutableViewType : MutableLiveData<Int> = MutableLiveData()
    val viewType : LiveData<Int> = mutableViewType

    private var recyclerViewType = RECYCLER_VIEW_TYPE_LIST
    private var tickerData = ArrayList<Model.Ticker>()

    private val apiService =  ApiService.create()
    private var disposable : Disposable? = null

    fun getTickers() {
        disposable = apiService.getTickers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->

                            if(result.success) {
                                tickerData = result.result.tickers
                                mutableTickers.postValue(tickerData)
                            }
                        },
                        { error -> error.printStackTrace() }
                )
    }

    fun switchViewType() {
        when (recyclerViewType) {
            RECYCLER_VIEW_TYPE_LIST -> recyclerViewType = RECYCLER_VIEW_TYPE_GRID
            RECYCLER_VIEW_TYPE_GRID -> recyclerViewType = RECYCLER_VIEW_TYPE_LIST
        }

        mutableViewType.postValue(recyclerViewType)
    }

    fun doSearch() {
        // dismiss keyboard
        val imm = getApplication<Application>().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activityBinding.mainSearchEdittext.windowToken, 0)

        // no input
        if(activityBinding.mainSearchEdittext.text.isEmpty()) {
            return
        }

        // get keyword and search
        var keyword : String = activityBinding.mainSearchEdittext.text.toString()

        var index = tickerData
                .withIndex()
                .indexOfFirst { keyword.equals(it.value.trading_pair_id, ignoreCase = true) }

        showSearchResult(index)
    }

    private fun showSearchResult(index: Int) {
        if(index >= 0) {
            activityBinding.mainReycclerview.smoothScrollToPosition(index)
        } else{
            Toast.makeText(getApplication(), "not found", Toast.LENGTH_SHORT).show()
        }

        activityBinding.adapter.selectTicker(index)
    }

    fun onPause() {
        disposable?.dispose()
    }

}

