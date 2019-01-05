package dev.reece.cobinhood.model

/**
 * model for list item
 * Created by reececheng on 2019/1/5.
 */
data class TickerItem(var name: String,
                      var percentage: String,
                      var lastPrice: String,
                      var selected: Boolean)
