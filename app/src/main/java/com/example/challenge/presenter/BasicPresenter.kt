package com.example.challenge.presenter

import com.example.challenge.model.Cell
import com.example.challenge.model.WolframProgression

class BasicPresenter(
    private val listener: WolframProgressionListener,
    private val capactity: Int = WolframProgression.DEFAULT_CAPACITY
) : WolframProgression.ProgressionListener {
    private val _wolfram = WolframProgression(capactity)

    val generationCapacity: Int
        get() = _wolfram.capacity
    val generationCount: Int
        get() = _wolfram.generations.size
    val progressionActive: Boolean
        get() = _wolfram.active

    fun generationContent(idx: Int): List<Cell> = _wolfram.generations[idx].cells

    init {
        _wolfram.registerListener(this)
    }

    fun startProgression() {
        _wolfram.startLife()
    }

    fun pauseProgression() {
        _wolfram.pauseLife()
    }


    override fun onNewGenerationSpawned(wolfram: WolframProgression) {
        listener.bindNewGeneration()
    }

    interface WolframProgressionListener {
        fun bindNewGeneration()
    }
}