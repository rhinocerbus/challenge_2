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
import com.example.challenge.presenter.BasicPresenter

open class BasicWolframAdapter(val presenter: BasicPresenter) : RecyclerView.Adapter<BasicWolframAdapter.ViewHolder>(){

    init {
        setHasStableIds(true)
    }

    fun updateData() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return presenter.generationCapacity * presenter.generationCount
    }

    override fun getItemId(position: Int): Long {
        val cap = presenter.generationCapacity
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
                val cap = presenter.generationCapacity
                val gen = position/cap
                val cellIdx = (position - (gen*cap))
                holder.bindView(presenter.generationContent(gen)[cellIdx])
            }
        }
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    open class BasicViewHolder(itemView: View) : ViewHolder(itemView) {
        @BindView(R.id.root) lateinit var root: FrameLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        open fun bindView(cell: Cell) {
            val colorId = if(cell.active) R.color.basicActive else R.color.basicInactive
            root.setBackgroundColor(ResourcesCompat.getColor(root.resources, colorId, null))
        }
    }

    companion object {
        const val VIEW_TYPE_CELL_BASIC = 0
    }
}