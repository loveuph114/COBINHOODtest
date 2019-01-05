package dev.reece.cobinhood.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by reececheng on 2019/1/5.
 */
abstract class TickerViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(data: T, selected: Boolean)
}
