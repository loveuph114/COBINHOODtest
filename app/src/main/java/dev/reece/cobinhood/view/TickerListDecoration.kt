package dev.reece.cobinhood.view

import android.content.res.Resources
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import dev.reece.cobinhood.viewmodel.MainViewModel

/**
 * Created by reececheng on 2019/1/5.
 */
class TickerListDecoration : RecyclerView.ItemDecoration() {
    var viewType : Int = MainViewModel.RECYCLER_VIEW_TYPE_LIST

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        var space = pxToDp(8)

        if(viewType == MainViewModel.RECYCLER_VIEW_TYPE_GRID) {
            var position = parent.getChildAdapterPosition(view)

            if(position % 2 == 0) {
                // left side

                outRect.left = space
                outRect.right = space/2

            } else {
               // right side

                outRect.left = space/2
                outRect.right = space
            }

            outRect.bottom = space
        } else{

            outRect.left = space
            outRect.right = space
            outRect.bottom = space
        }

    }

    private fun pxToDp(px: Int) : Int {
        return (px * Resources.getSystem().displayMetrics.density).toInt()
    }

}