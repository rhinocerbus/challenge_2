package com.example.challenge.model

import com.example.challenge.model.WolframProgression.Companion.DEFAULT_CAPACITY
import com.example.challenge.util.backgroundToMain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.TestOnly
import java.security.InvalidParameterException
import java.util.concurrent.TimeUnit

data class WolframProgression(val capacity: Int = DEFAULT_CAPACITY) : BaseData {
    private val cellGenerations: ArrayList<Generation> = ArrayList()
    val maxGenerations = ((capacity - 1) / 2.0).toInt() + 1

    private var listeners: ArrayList<ProgressionListener> = arrayListOf()
    var active = false
        private set

    private val evolveRate = EVOLVE_RATE_TARGET / maxGenerations
    private var _composite = CompositeDisposable()

    val activeCount: Int
        get() {
            var count = 0
            for (g in generations) {
                count += g.activeCount
            }
            return count
        }
    val generations: Array<Generation>
        get() = cellGenerations.toTypedArray()

    init {
        if (capacity <= 0) throw InvalidParameterException("needs room to grow")
        if (capacity % 2 == 0) throw InvalidParameterException("Even numbers aren't symmetrical, going to have a bad time")
    }

    override fun reset() {
        cellGenerations.clear()
    }

    fun registerListener(l: ProgressionListener) {
        if (!listeners.contains(l)) listeners.add(l)
    }

    fun unregisterListener(l: ProgressionListener) {
        listeners.remove(l)
    }

    fun startLife() {
        if (!active && generations.size < maxGenerations) {
            active = true
            _composite = CompositeDisposable()
            _composite.add(
                buildEvolveObservable()
                    .subscribe {
                    for (listener in listeners) {
                        listener.onNewGenerationSpawned(this)
                        if (active && generations.size >= maxGenerations) {
                            pauseLife()
                        }
                    }
                }
            )
        }
    }

    fun pauseLife() {
        if (active) {
            active = false
            _composite.dispose()
        }
    }

    private fun buildEvolveObservable(): Observable<Boolean> {
        return Observable
            .interval(evolveRate, evolveRate, TimeUnit.MILLISECONDS, Schedulers.io())
            .map {
                advance()
                true
            }
            .take((maxGenerations - generations.size).toLong())
            .backgroundToMain()
    }

    private fun advance() {
        mutate()
    }

    private fun mutate() {
        val newGeneration = Generation(capacity)
        newGeneration.spawn(if (cellGenerations.size > 0) cellGenerations[cellGenerations.size - 1] else null)
        cellGenerations.add(newGeneration)
    }

    @TestOnly
    fun manualAdvance() {
        advance()
    }

    @TestOnly
    fun activeCountForGeneration(idx: Int): Int {
        return cellGenerations[idx].activeCount
    }

    interface ProgressionListener {
        fun onNewGenerationSpawned(wolfram: WolframProgression)
    }

    companion object {
        const val DEFAULT_CAPACITY = 31
        const val EVOLVE_RATE_TARGET = 10000L
    }
}

data class Generation(
    private val capacity: Int = DEFAULT_CAPACITY,
    private val _cells: List<Cell> = List(capacity, init = { i: Int -> Cell() })
) : BaseData {

    private var _activeCount = 0
    val activeCount: Int
        get() = _activeCount
    val cells: List<Cell>
        get() = _cells

    override fun reset() {
        for (cell in _cells) {
            cell.reset()
        }
        _activeCount = 0
    }

    fun spawn(prevGeneration: Generation?) {
        _activeCount = 0
        if (prevGeneration == null) {
            val idx = (capacity / 2.0).toInt()
            _cells[idx].updateIsActive(true)
            ++_activeCount
            return
        }

        val oldCells = prevGeneration._cells
        for (idx in 0 until capacity) {
            //[left_cell XOR (central_cell OR right_cell)]
            val active = when (idx) {
                0 -> oldCells[idx].active || oldCells[idx + 1].active
                oldCells.size - 1 -> oldCells[idx - 1].active xor oldCells[idx].active
                else -> oldCells[idx - 1].active xor (oldCells[idx].active || oldCells[idx + 1].active)
            }
            if (active)
                ++_activeCount

            _cells[idx].updateIsActive(active)
        }
    }
}

data class Cell(
    private var _stateDelta: Double = 0.0,
    private var _active: Boolean = false
) : BaseData {

    val active: Boolean
        get() = _active

    override fun reset() {
        _active = false
        _stateDelta = 0.0
    }

    fun updateIsActive(isActive: Boolean) {
        if (isActive == _active) return
        if (isActive) activate() else deactivate()
    }

    private fun activate() {
        _active = true
    }

    private fun deactivate() {
        _active = false
    }

}

interface BaseData {
    fun reset()
}
