package tat.mukhutdinov.demiurgecells.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import tat.mukhutdinov.demiurgecells.R
import tat.mukhutdinov.demiurgecells.databinding.CellItemBinding
import tat.mukhutdinov.demiurgecells.model.Cell
import tat.mukhutdinov.demiurgecells.model.CellType

class CellViewHolder(private val binding: CellItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cell: Cell) {
        when (cell.type) {
            CellType.ALIVE -> buildView(R.string.alive_title, R.string.alive_message, R.drawable.background_alive, R.drawable.alive)
            CellType.LIFE -> buildView(R.string.life_title, R.string.life_message, R.drawable.background_life, R.drawable.life)
            CellType.DEAD -> buildView(R.string.dead_title, R.string.dead_message, R.drawable.background_dead, R.drawable.dead)
            CellType.DEATH -> buildView(R.string.death_title, R.string.death_message, R.drawable.background_death, R.drawable.life)
        }
    }

    private fun buildView(@StringRes title: Int, @StringRes message: Int, @DrawableRes background: Int, @DrawableRes icon: Int) {
        binding.title.text = itemView.context.getString(title)
        binding.message.text = itemView.context.getString(message)

        binding.icon.background = ContextCompat.getDrawable(itemView.context, background)
        binding.icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, icon))
    }

    companion object {

        fun create(parent: ViewGroup): CellViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return CellViewHolder(CellItemBinding.inflate(inflater, parent, false))
        }
    }
}