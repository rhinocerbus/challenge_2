package com.example.challenge.view.rave

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.example.challenge.R
import com.example.challenge.model.Cell
import com.example.challenge.view.shared.BasicWolframAdapter
import java.lang.IllegalStateException


class RaveWolframAdapter() : BasicWolframAdapter() {

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ANIMATED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ANIMATED -> {
                AnimatedViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_cell_basic, parent, false)
                )
            }

            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is AnimatedViewHolder -> {
                val cap = data.capacity
                val gen = position/cap
                val cellIdx = (position - (gen*cap))
                holder.bindView(data.generations[gen].cells[cellIdx])
            }
        }
    }

    companion object {
        const val VIEW_TYPE_ANIMATED = 1
    }

    class AnimatedViewHolder(itemView: View) : BasicWolframAdapter.BasicViewHolder(itemView) {
        var animation: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
        var animation2: ValueAnimator = ValueAnimator.ofFloat(1f, 0f)
        var isAnimating = false

        init {
            animation.duration = 2000

            var runColor: Int
            val hue = 0
            val hsv: FloatArray = FloatArray(3) // Transition color

            hsv[1] = 1.0f
            hsv[2] = 1.0f
            animation.addUpdateListener { animation ->
                hsv[0] = 360 * animation.animatedFraction
                runColor = Color.HSVToColor(hsv)
                root2.setBackgroundColor(runColor)
            }
            animation.repeatCount = Animation.INFINITE

            animation2.duration = 2000

            hsv[1] = 1.0f
            hsv[2] = 1.0f
            animation2.addUpdateListener { animation ->
                hsv[0] = 360 - (360 * animation.animatedFraction)
                runColor = Color.HSVToColor(hsv)
                root2.setBackgroundColor(runColor)
            }
            animation2.repeatCount = Animation.INFINITE
        }

        override fun bindView(cell: Cell) {
            super.bindView(cell)

            if(cell.active) {
                if(!animation.isRunning) {
                    animation.start()
                    animation2.pause()
                }
            } else {
                if(!animation2.isRunning) {
                    animation2.start()
                    animation.pause()
                }
            }
        }
    }
}