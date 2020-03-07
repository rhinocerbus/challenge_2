package com.example.challenge.view.rave

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.example.challenge.R
import com.example.challenge.model.Cell
import com.example.challenge.presenter.BasicPresenter
import com.example.challenge.view.shared.BasicWolframAdapter
import java.lang.IllegalStateException


class RaveWolframAdapter(presenter: BasicPresenter) : BasicWolframAdapter(presenter) {

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
                val cap = presenter.generationCapacity
                val gen = position/cap
                val cellIdx = (position - (gen*cap))
                holder.bindView(presenter.generationContent(gen)[cellIdx])
            }
        }
    }

    companion object {
        const val VIEW_TYPE_ANIMATED = 1
    }

    class AnimatedViewHolder(itemView: View) : BasicWolframAdapter.BasicViewHolder(itemView) {
        var colorAnim: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
        var invertedColorAnim: ValueAnimator = ValueAnimator.ofFloat(1f, 0f)

        init {
            colorAnim.duration = 2000

            var runColor: Int
            val hsv = FloatArray(3)

            hsv[1] = 1.0f
            hsv[2] = 1.0f
            colorAnim.addUpdateListener { animation ->
                hsv[0] = 360 * animation.animatedFraction
                runColor = Color.HSVToColor(hsv)
                root.setBackgroundColor(runColor)
            }
            colorAnim.repeatCount = Animation.INFINITE

            invertedColorAnim.duration = 2000

            hsv[1] = 1.0f
            hsv[2] = 1.0f
            invertedColorAnim.addUpdateListener { animation ->
                hsv[0] = 360 - (360 * animation.animatedFraction)
                runColor = Color.HSVToColor(hsv)
                root.setBackgroundColor(runColor)
            }
            invertedColorAnim.repeatCount = Animation.INFINITE
        }

        override fun bindView(cell: Cell) {
            super.bindView(cell)

            if(cell.active) {
                if(!colorAnim.isRunning) {
                    colorAnim.start()
                    invertedColorAnim.pause()
                }
            } else {
                if(!invertedColorAnim.isRunning) {
                    invertedColorAnim.start()
                    colorAnim.pause()
                }
            }
        }
    }
}