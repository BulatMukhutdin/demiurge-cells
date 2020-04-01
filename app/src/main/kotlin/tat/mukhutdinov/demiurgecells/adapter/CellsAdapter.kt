package tat.mukhutdinov.demiurgecells.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tat.mukhutdinov.demiurgecells.model.Cell

class CellsAdapter : RecyclerView.Adapter<CellViewHolder>() {

    private val cells = mutableListOf<Cell>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder =
        CellViewHolder.create(parent)

    override fun getItemCount(): Int =
        cells.size

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) =
        holder.bind(cells[position])

    override fun getItemId(position: Int): Long =
        cells[position].id

    fun submitList(updated: List<Cell>) {
        val diffResult = DiffUtil.calculateDiff(CellsDiffUtil(cells, updated))
        diffResult.dispatchUpdatesTo(this)

        cells.clear()
        cells.addAll(updated)
    }

    class CellsDiffUtil(
        private val oldList: List<Cell>,
        private val newList: List<Cell>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize(): Int =
            oldList.size

        override fun getNewListSize(): Int =
            newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].type == newList[newItemPosition].type
    }
}