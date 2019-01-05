package dev.reece.cobinhood.viewholder

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dev.reece.cobinhood.R
import dev.reece.cobinhood.data.Model
import java.math.BigDecimal

/**
 * Created by reececheng on 2019/1/5.
 */
class TickerGridViewHolder constructor(itemView : View) : TickerViewHolder<Model.Ticker>(itemView) {

    private var tickerPairIdText : TextView = itemView.findViewById(R.id.ticker_grid_trading_pair_id)
    private var percentageText : TextView = itemView.findViewById(R.id.ticker_grid_percentage)
    private var lastTradePriceText: TextView = itemView.findViewById(R.id.ticker_grid_last_trade_price)
    private val selectedImg : ImageView = itemView.findViewById(R.id.ticker_grid_selected);

    override fun bind(data: Model.Ticker, selected: Boolean) {
        tickerPairIdText.text = data.trading_pair_id

        lastTradePriceText.text = BigDecimal.valueOf(data.last_trade_price).toString()


        var percentage = (data.last_trade_price - data.h_open24) / data.h_open24 * 100

        if(percentage >= 0) {
            percentageText.setTextColor(Color.parseColor("#00DD00"))
        } else{
            percentageText.setTextColor(Color.parseColor("#CC0000"))
        }

        percentageText.text = String.format(itemView.resources.getString(R.string.percentage_format), percentage)


        if(selected) {
            selectedImg.visibility = View.VISIBLE
        } else{
            selectedImg.visibility = View.GONE
        }
    }
}