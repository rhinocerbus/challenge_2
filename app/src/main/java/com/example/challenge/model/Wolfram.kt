package com.example.challenge.model

import com.example.challenge.model.WolframProgression.Companion.DEFAULT_CAPACITY
import com.example.challenge.util.backgroundToMain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.TestOnly
import org.reactivestreams.Subscription
import java.security.InvalidParameterException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

data class WolframProgression(val capacity: Int = DEFAULT_CAPACITY) : BaseData {
    private val cellGenerations: ArrayList<Generation> = ArrayList()
    private var generation = 0
    private var active = false
    private var _activeCount = 0
    val maxGenerations = ((capacity - 1) / 2.0).toInt() + 1

    private var listeners: ArrayList<ProgressionListener> = arrayListOf()

    private val _evolveDisp: Disposable by lazy {
        Observable
            .interval(EVOLVE_RATE, EVOLVE_RATE, TimeUnit.MILLISECONDS)
            .map {
                advance()
            }
            .backgroundToMain()
            .subscribe {
                for(listener in listeners) {
                    listener.onNewGenerationSpawned(this)
                    if(active && generations.size >= maxGenerations) {
                        pauseLife()
                    }
                }
            }
    }
    private var _composite = CompositeDisposable()

    val activeCount: Int
        get() {
            var count = 0
            for(g in generations) {
                count += g.activeCount
            }
            return count
        }
    val generations: Array<Generation>
        get() = cellGenerations.toTypedArray()

    init {
        if(capacity <= 0) throw InvalidParameterException("needs room to grow")
        if(capacity % 2 == 0) throw InvalidParameterException("Even numbers aren't symmetrical, going to have a bad time")
    }

    override fun initialize() {
        cellGenerations.clear()
    }

    fun registerListener(l: ProgressionListener) {
        if(!listeners.contains(l)) listeners.add(l)
    }

    fun unregisterListener(l: ProgressionListener) {
        listeners.remove(l)
    }

    fun startLife() {
        active = true
        _composite.add(_evolveDisp)
    }

    fun pauseLife() {
        active = false
        _composite.remove(_evolveDisp)
    }

    private fun advance() {
        mutate()
        ++generation
    }

    private fun mutate() {
        val newGeneration = Generation(capacity)
        newGeneration.spawn(if(cellGenerations.size > 0) cellGenerations[cellGenerations.size - 1] else null)
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
        const val EVOLVE_RATE = 500L
    }
}

data class Generation(
    private val capacity: Int = DEFAULT_CAPACITY,
    private val _cells: List<Cell> = List(capacity, init = {i: Int -> Cell() })
) : BaseData {

    private var _activeCount = 0
    val activeCount: Int
        get() = _activeCount
    val cells: List<Cell>
        get() = _cells

    override fun initialize() {
        for(cell in _cells) {
            cell.initialize()
        }
        _activeCount = 0
    }

    fun spawn(prevGeneration: Generation?) {
        _activeCount = 0
        if(prevGeneration == null) {
            val idx = (capacity / 2.0).toInt()
            _cells[idx].updateIsActive(true)
            ++_activeCount
            return
        }

        val oldCells = prevGeneration._cells
        for(idx in 0 until capacity) {
            //[left_cell XOR (central_cell OR right_cell)]
            val active = when(idx) {
                0 -> oldCells[idx].active || oldCells[idx+1].active
                oldCells.size-1 -> oldCells[idx-1].active xor oldCells[idx].active
                else -> oldCells[idx-1].active xor (oldCells[idx].active || oldCells[idx+1].active)
            }
            if(active)
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

    override fun initialize() {
        _active = false
        _stateDelta = 0.0
    }

    fun updateIsActive(isActive: Boolean) {
        if(isActive == _active) return
        if(isActive) activate() else deactivate()
    }

    private fun activate() {
        _active = true
    }

    private fun deactivate() {
        _active = false
    }

}

interface BaseData {
    fun initialize()
}
