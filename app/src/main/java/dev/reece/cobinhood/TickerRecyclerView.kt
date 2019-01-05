package dev.reece.cobinhood

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by reececheng on 2019/1/5.
 */
class TickerRecyclerView (context: Context?, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {

    companion object {
        val LIST = 2
        val GRID = 1
    }

    var viewSpan : Int = LIST

    init {
        var layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(i: Int): Int {
                return viewSpan
            }
        }

        this.layoutManager = layoutManager
    }



}