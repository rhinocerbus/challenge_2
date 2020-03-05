package com.example.challenge.view.anim1

import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class RippleItemAnimator(val spanCount: Int) : SimpleItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val pos = holder.adapterPosition
        val row = (pos / spanCount).toInt()
        val column = pos - (row*spanCount)
        val center = spanCount / 2
        val colOffset = Math.abs(center - column)
        val anim = AlphaAnimation(0f, 1f)
        anim.duration = 500
        anim.startOffset = colOffset * 100L
        holder.itemView.startAnimation(anim)
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        return false
    }

    override fun isRunning(): Boolean {
        return false
    }

    override fun runPendingAnimations() {
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
    }

    override fun endAnimations() {
    }
}
