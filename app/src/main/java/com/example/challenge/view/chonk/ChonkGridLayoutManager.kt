package com.example.challenge.view.chonk

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChonkGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {
    private var tileSize: Int

    init {
        tileSize = calculateDimensFromSpan(context, spanCount)
    }

    private fun calculateDimensFromSpan(
        context: Context,
        span: Int
    ): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        val screenW = metrics.widthPixels.toFloat()
        return (screenW / span).toInt()
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): RecyclerView.LayoutParams? {
        val gridLp = super.generateLayoutParams(lp)
        gridLp.height = tileSize
        gridLp.width = tileSize
        return gridLp
    }

    override fun generateLayoutParams(
        c: Context?,
        attrs: AttributeSet?
    ): RecyclerView.LayoutParams? {
        val gridLp = super.generateLayoutParams(c, attrs)
        gridLp.height = tileSize
        gridLp.width = tileSize
        return gridLp
    }
}