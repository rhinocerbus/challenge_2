package com.example.challenge

import com.example.challenge.model.WolframProgression
import com.example.challenge.presenter.BasicPresenter
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PresenterTests {

    @Test
    fun generationCheck() {
        val listener = object : BasicPresenter.WolframProgressionListener {
            override fun bindNewGeneration() {
                //callback received
                assert(true)
            }
        }
        val presenter = BasicPresenter(listener)
        assertEquals(31, presenter.generationCapacity)
    }
}
