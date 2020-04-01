package tat.mukhutdinov.demiurgecells

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tat.mukhutdinov.demiurgecells.model.Cell
import tat.mukhutdinov.demiurgecells.model.CellType
import java.util.*
import kotlin.random.Random

class MainViewModel : ViewModel() {

    val cells = MutableLiveData<MutableList<Cell>>()

    private var currentId = 0L

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Exception inside coroutine", exception)
    }

    fun create() {
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
            val newsState = buildNewState()

            cells.postValue(newsState)
        }
    }

    private fun buildNewState(): MutableList<Cell> {
        val isAlive = Random.nextBoolean()

        val type = if (isAlive) {
            CellType.ALIVE
        } else {
            CellType.DEAD
        }

        val newCell = Cell(currentId++, type)

        val cells = cells.value ?: LinkedList()

        cells.add(0, newCell)

        tryToBornOrKill(cells)

        return cells
    }

    private fun tryToBornOrKill(cells: MutableList<Cell>) {
        if (cells.size < 3) {
            return
        }

        var previous = cells[0]

        cells.subList(0, 3).forEach { current ->
            if (previous.type != current.type) {
                return
            }

            previous = current
        }

        if (previous.type == CellType.ALIVE) {
            cells.add(0, Cell(currentId++, CellType.LIFE))
        }

        if (previous.type == CellType.DEAD) {
            val lastLifeIndex = cells.indexOfFirst { it.type == CellType.LIFE }

            if (lastLifeIndex != -1) {
                val dead = cells.removeAt(lastLifeIndex)
                cells.add(lastLifeIndex, Cell(dead.id, CellType.DEATH))
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}