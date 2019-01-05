package dev.reece.cobinhood.view

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import dev.reece.cobinhood.R
import dev.reece.cobinhood.databinding.ActivityMainBinding
import dev.reece.cobinhood.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var decoration : TickerListDecoration

    private lateinit var activityBinding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = MainViewModel(application, activityBinding)
        activityBinding.viewModel = viewModel

        // fetch tickers
        viewModel.tickers.observe(this, Observer {
            activityBinding.adapter.setTickers(it ?: arrayListOf())
        })

        // switch view type
        viewModel.viewType.observe(this, Observer {
            val type = it ?: MainViewModel.RECYCLER_VIEW_TYPE_LIST

            activityBinding.adapter.viewType = type
            decoration.viewType = type

            activityBinding.mainReycclerview.layoutManager = GridLayoutManager(this, type)
        })

        // for init
        activityBinding.mainReycclerview.layoutManager = GridLayoutManager(this, 1)

        decoration = TickerListDecoration()
        activityBinding.adapter = TickerListAdapter()
        activityBinding.mainReycclerview.addItemDecoration(decoration)
    }


    override fun onResume() {
        super.onResume()
        viewModel.getTickers()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }


}
