package com.simpleapp.peikytest.Utils


import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewPage internal constructor(private val recyclerView: RecyclerView) {
    internal val layoutManager: RecyclerView.LayoutManager? = recyclerView.getLayoutManager()

    val itemCount: Int
        get() = if (layoutManager == null) 0 else layoutManager!!.getItemCount()

    fun findFirstVisibleItemPosition(): Int {
        val child = findOneVisibleChild(0, layoutManager!!.childCount, false, true)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(child)
    }

    fun findFirstCompletelyVisibleItemPosition(): Int {
        val child = findOneVisibleChild(0, layoutManager!!.childCount, true, false)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(child)
    }

    fun findLastVisibleItemPosition(): Int {
        val child = findOneVisibleChild(layoutManager!!.childCount - 1, -1, false, true)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(child)
    }

    fun findLastCompletelyVisibleItemPosition(): Int {
        val child = findOneVisibleChild(layoutManager!!.childCount - 1, -1, true, false)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(child)
    }

    fun findOneVisibleChild(fromIndex: Int, toIndex: Int, completelyVisible: Boolean,
                            acceptPartiallyVisible: Boolean): View? {
        val helper: OrientationHelper
        if (layoutManager!!.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(layoutManager)
        } else {
            helper = OrientationHelper.createHorizontalHelper(layoutManager)
        }

        val start = helper.getStartAfterPadding()
        val end = helper.getEndAfterPadding()
        val next = if (toIndex > fromIndex) 1 else -1
        var partiallyVisible: View? = null
        var i = fromIndex
        while (i != toIndex) {
            val child = layoutManager!!.getChildAt(i)
            val childStart = helper.getDecoratedStart(child)
            val childEnd = helper.getDecoratedEnd(child)
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child
                    }
                } else {
                    return child
                }
            }
            i += next
        }
        return partiallyVisible
    }

    companion object {
        fun createHelper(recyclerView: RecyclerView?): RecyclerViewPage {
            if (recyclerView == null) {
                throw NullPointerException("Recycler View is null")
            }
            return RecyclerViewPage(recyclerView!!)
        }
    }
}