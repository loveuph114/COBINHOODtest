package dev.reece.cobinhood

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import dev.reece.cobinhood.data.ApiService
import dev.reece.cobinhood.data.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    companion object {
        val RECYCLER_VIEW_TYPE_LIST = 1
        val RECYCLER_VIEW_TYPE_GRID = 2
    }

    private val apiService by lazy {
        ApiService.create()
    }

    private var recyclerViewType : Int = RECYCLER_VIEW_TYPE_LIST

    private var disposable: Disposable? = null

    private lateinit var switchBtn : Button

    private lateinit var searchBtn : Button
    private lateinit var searchEdittext : EditText

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : TickerListAdapter
    private lateinit var decoration : TickerListDecoration

    private var tickers = arrayListOf<Model.Ticker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchBtn = findViewById(R.id.main_switch_mode_btn)
        switchBtn.setOnClickListener { switchViewType() }

        searchBtn = findViewById(R.id.main_search_btn)
        searchBtn.setOnClickListener { doSearch() }

        searchEdittext = findViewById(R.id.main_search_edittext)

        recyclerView = findViewById(R.id.main_reycclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        decoration = TickerListDecoration()
        adapter = TickerListAdapter()

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(decoration)
    }


    private fun switchViewType() {
        when (recyclerViewType) {
            RECYCLER_VIEW_TYPE_LIST -> {
                recyclerViewType = RECYCLER_VIEW_TYPE_GRID
                adapter.viewType = RECYCLER_VIEW_TYPE_GRID
                decoration.viewType = RECYCLER_VIEW_TYPE_GRID

                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }

            RECYCLER_VIEW_TYPE_GRID -> {
                recyclerViewType = RECYCLER_VIEW_TYPE_LIST
                adapter.viewType = RECYCLER_VIEW_TYPE_LIST
                decoration.viewType = RECYCLER_VIEW_TYPE_LIST

                recyclerView.layoutManager = GridLayoutManager(this, 1)
            }
        }
    }

    private fun doSearch() {
        // dismiss keyboard
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEdittext.windowToken, 0)

        // no input
        if(searchEdittext.text.isEmpty()) {
            return
        }

        // get keyword and search
        var keyword : String = searchEdittext.text.toString()

        var index = tickers
                .withIndex()
                .indexOfFirst { keyword.equals(it.value.trading_pair_id, ignoreCase = true) }

        showSearchResult(index)
    }

    private fun showSearchResult(index: Int) {
        if(index >= 0) {
            recyclerView.smoothScrollToPosition(index)
        } else{
            Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
        }

        adapter.selectTicker(index)
    }

    override fun onResume() {
        super.onResume()
        getTickers()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun getTickers() {
        disposable = apiService.getTickers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result -> Log.d("reece", "result success ${result.success}")

                            if(result.success) {
                                show(result.result.tickers)
                            }
                        },
                        { error -> error.printStackTrace() }
                )
    }

    private fun show(tickers: ArrayList<Model.Ticker>) {
        Log.d("reece", "tickers: ${tickers.size}")

        this.tickers = tickers
        adapter.setTickers(tickers)
    }




}
