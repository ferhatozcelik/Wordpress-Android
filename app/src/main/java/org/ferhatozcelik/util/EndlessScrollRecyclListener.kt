package org.ferhatozcelik.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author ferhatozcelik
 * @since 2023-04-02
 */

abstract class EndlessScrollRecyclListener : RecyclerView.OnScrollListener() {
    private var previousTotalItemCount = 0
    private var loading = true
    private var visibleThreshold = 10
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var startingPageIndex = 1
    private var currentPage: Int = 0

    override fun onScrolled(mRecyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(mRecyclerView, dx, dy)
        val mLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager?
        visibleItemCount = mRecyclerView.childCount
        totalItemCount = mLayoutManager!!.itemCount
        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
        onScroll(firstVisibleItem, visibleItemCount, totalItemCount)
    }

    fun onScroll(firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            onLoadMore(currentPage + 1, totalItemCount)
            loading = true
        }
    }

    fun reset() {
        previousTotalItemCount = 0
        loading = true
        visibleThreshold = 10
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
        startingPageIndex = 1
        currentPage = 0

    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
}