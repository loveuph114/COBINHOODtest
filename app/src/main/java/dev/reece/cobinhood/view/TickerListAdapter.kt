package dev.reece.cobinhood.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import dev.reece.cobinhood.R
import dev.reece.cobinhood.databinding.TickerGridItemViewBinding
import dev.reece.cobinhood.databinding.TickerListItemViewBinding
import dev.reece.cobinhood.model.Model
import dev.reece.cobinhood.view.viewholder.TickerGridViewHolder
import dev.reece.cobinhood.view.viewholder.TickerListViewHolder
import dev.reece.cobinhood.view.viewholder.TickerViewHolder
import dev.reece.cobinhood.viewmodel.MainViewModel

/**
 * Created by reececheng on 2019/1/5.
 */
class TickerListAdapter : RecyclerView.Adapter<TickerViewHolder<Model.Ticker>>() {

    var viewType : Int = MainViewModel.RECYCLER_VIEW_TYPE_LIST

    private var tickers = arrayListOf<Model.Ticker>()
    private var selected : Int? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TickerViewHolder<Model.Ticker> {
        val inflater = LayoutInflater.from(parent?.context)

        when (viewType) {
            MainViewModel.RECYCLER_VIEW_TYPE_LIST -> {
                var viewBinding = DataBindingUtil.inflate<TickerListItemViewBinding>(
                        inflater,
                        R.layout.ticker_list_item_view,
                        parent,
                        false)

                return TickerListViewHolder(viewBinding)
            }

            MainViewModel.RECYCLER_VIEW_TYPE_GRID -> {
                var viewBinding = DataBindingUtil.inflate<TickerGridItemViewBinding>(
                        inflater,
                        R.layout.ticker_grid_item_view,
                        parent,
                        false)

                return TickerGridViewHolder(viewBinding)
            }

            // by default use list mode
            else -> {
                var viewBinding = DataBindingUtil.inflate<TickerListItemViewBinding>(
                        inflater,
                        R.layout.ticker_list_item_view,
                        parent,
                        false)

                return TickerListViewHolder(viewBinding)
            }
        }

    }

    override fun onBindViewHolder(holder: TickerViewHolder<Model.Ticker>?, position: Int) {
        holder?.bind(tickers[position], selected == position)
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun getItemCount(): Int {
        return tickers.size
    }

    fun setTickers(tickers: ArrayList<Model.Ticker>) {
        this.tickers = tickers
        notifyDataSetChanged()
    }

    fun selectTicker(index: Int) {
        selected = if(index >= 0) {
            index
        } else{
            null
        }

        notifyDataSetChanged()
    }

}