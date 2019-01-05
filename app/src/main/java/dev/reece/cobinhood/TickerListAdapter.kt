package dev.reece.cobinhood

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import dev.reece.cobinhood.data.Model
import dev.reece.cobinhood.viewholder.TickerGridViewHolder
import dev.reece.cobinhood.viewholder.TickerListViewHolder
import dev.reece.cobinhood.viewholder.TickerViewHolder

/**
 * Created by reececheng on 2019/1/5.
 */
class TickerListAdapter : RecyclerView.Adapter<TickerViewHolder<Model.Ticker>>() {

    var viewType : Int = MainActivity.RECYCLER_VIEW_TYPE_LIST

    private var tickers = arrayListOf<Model.Ticker>()
    private var selected : Int? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TickerViewHolder<Model.Ticker> {
        val inflater = LayoutInflater.from(parent?.context)

        when (viewType) {
            MainActivity.RECYCLER_VIEW_TYPE_LIST -> {
                val view = inflater.inflate(R.layout.ticker_list_item_view, parent, false)
                return TickerListViewHolder(view)
            }

            MainActivity.RECYCLER_VIEW_TYPE_GRID -> {
                val view = inflater.inflate(R.layout.ticker_grid_item_view, parent, false)
                return TickerGridViewHolder(view)
            }

            // by default use list mode
            else -> {
                val view = inflater.inflate(R.layout.ticker_list_item_view, parent, false)
                return TickerListViewHolder(view)
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