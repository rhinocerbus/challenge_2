package com.example.challenge.view.fancy

import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge.view.shared.RippleItemAnimator

class FancyItemAnimator(spanCount: Int, val tileSize: Int) : RippleItemAnimator(spanCount) {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val animSet = AnimationSet(false)

        val pos = holder.adapterPosition
        val fadeAnimation = super.buildFadeAnimation(pos)
        animSet.addAnimation(fadeAnimation)

        val row = (pos / spanCount).toInt()
        val column = pos - (row*spanCount)
        val center = spanCount / 2
        val colOffset = (column - center)
        val absOffset = Math.abs(colOffset)

        val progression = (colOffset.toFloat()/center.toFloat()).toFloat()
        val maxAngle = -80f
        val angle = (maxAngle*progression).toFloat()

        val pivotX: Float
        val pivotY: Float = tileSize.toFloat()
        pivotX = if(colOffset > 0) {
            0f
        } else {
            tileSize.toFloat()
        }


        val anim2 = RotateAnimation(angle, 0f, pivotX, pivotY)
        anim2.duration = 500
        anim2.startOffset = absOffset * 100L
        animSet.addAnimation(anim2)

        holder.itemView.startAnimation(animSet)

        return true
    }
}
