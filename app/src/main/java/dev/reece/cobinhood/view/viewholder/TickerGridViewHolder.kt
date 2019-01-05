package dev.reece.cobinhood.view.viewholder

import android.graphics.Color
import android.view.View
import dev.reece.cobinhood.R
import dev.reece.cobinhood.databinding.TickerGridItemViewBinding
import dev.reece.cobinhood.model.Model
import dev.reece.cobinhood.model.TickerItem
import java.math.BigDecimal

/**
 * Created by reececheng on 2019/1/5.
 */
class TickerGridViewHolder constructor(viewBinding : TickerGridItemViewBinding) : TickerViewHolder<Model.Ticker>(viewBinding.root) {

    private var viewBinding = viewBinding

    override fun bind(data: Model.Ticker, selected: Boolean) {

        val percentage = (data.last_trade_price - data.h_open24) / data.h_open24 * 100

        if(percentage >= 0) {
            viewBinding.tickerGridPercentage.setTextColor(Color.parseColor("#00DD00"))
        } else{
            viewBinding.tickerGridPercentage.setTextColor(Color.parseColor("#CC0000"))
        }

        if(selected) {
            viewBinding.tickerGridSelected.visibility = View.VISIBLE
        } else{
            viewBinding.tickerGridSelected.visibility = View.GONE
        }

        viewBinding.ticker = TickerItem(data.trading_pair_id,
                String.format(itemView.resources.getString(R.string.percentage_format), percentage),
                BigDecimal.valueOf(data.last_trade_price).toString(),
                selected)

        viewBinding.executePendingBindings()
    }
}