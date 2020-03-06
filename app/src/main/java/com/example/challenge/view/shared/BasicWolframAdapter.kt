package com.example.challenge.view.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.challenge.R
import com.example.challenge.model.Cell
import com.example.challenge.model.WolframProgression
import java.lang.IllegalStateException

open class BasicWolframAdapter() : RecyclerView.Adapter<BasicWolframAdapter.ViewHolder>(){
    lateinit var data: WolframProgression

    init {
        setHasStableIds(true)
    }

    fun updateData(wolfram: WolframProgression) {
        data = wolfram
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.capacity * data.generations.size
    }

    override fun getItemId(position: Int): Long {
        val cap = data.capacity
        val gen = position/cap
        val cellIdx = (position - (gen*cap))
        return ((gen*1000) + cellIdx).toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_CELL_BASIC
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
             VIEW_TYPE_CELL_BASIC -> {
                 BasicViewHolder(
                     LayoutInflater.from(parent.context).inflate(R.layout.item_cell_basic, parent, false)
                 )
             }

            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is BasicViewHolder -> {
                val cap = data.capacity
                val gen = position/cap
                val cellIdx = (position - (gen*cap))
                holder.bindView(data.generations[gen].cells[cellIdx])
            }
        }
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    open class BasicViewHolder(itemView: View) : ViewHolder(itemView) {
        @BindView(R.id.root) lateinit var root: FrameLayout
        @BindView(R.id.root2) lateinit var root2: View

        init {
            ButterKnife.bind(this, itemView)
        }

        open fun bindView(cell: Cell) {
            val colorId = if(cell.active) R.color.basicActive else R.color.basicInactive
            root2.setBackgroundColor(ResourcesCompat.getColor(root2.resources, colorId, null))
        }
    }

    companion object {
        const val VIEW_TYPE_CELL_BASIC = 0
    }
}