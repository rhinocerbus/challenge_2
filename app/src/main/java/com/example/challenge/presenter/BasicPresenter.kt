package com.example.challenge.presenter

import com.example.challenge.model.Generation
import com.example.challenge.model.WolframProgression

class DefaultPresenter(private val listener: ViewListener) : WolframProgression.ProgressionListener {
    private val _wolfram = WolframProgression()

    val wolfram: WolframProgression
    get() = _wolfram

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
        listener.bindNewGeneration(wolfram)
    }

    interface ViewListener {
        fun bindNewGeneration(wolfram: WolframProgression)
    }
}