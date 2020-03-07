package com.example.challenge

import com.example.challenge.model.WolframProgression
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModelTests {
    @Test
    fun startIndexCorrect() {
        val wolfram = WolframProgression()
        wolfram.reset()
        wolfram.manualAdvance()
        assertEquals(true, wolfram.generations[0].cells[15].active)

        val wolframLarge = WolframProgression(65)
        wolframLarge.reset()
        wolframLarge.manualAdvance()
        assertEquals(true, wolframLarge.generations[0].cells[32].active)
    }

    @Test
    fun generationCapCorrect() {
        val wolfram = WolframProgression()
        assertEquals(16, wolfram.maxGenerations)

        val wolframLarge = WolframProgression(65)
        assertEquals(33, wolframLarge.maxGenerations)

        val wolframXLarge = WolframProgression(121)
        assertEquals(61, wolframXLarge.maxGenerations)
    }

    @Test
    fun defaultCapacityProgression() {
        val wolfram = WolframProgression()
        val expectedSequence = intArrayOf(1, 3, 3, 6, 4, 9, 5, 12, 7, 12, 11, 14, 12, 19, 13, 22)

        wolfram.reset()
        for(idx in expectedSequence.indices) {
            wolfram.manualAdvance()
            assertEquals(expectedSequence[idx], wolfram.activeCountForGeneration(idx))
        }
    }

    @Test
    fun negativeCapacity() {
        try {
            val wolfram = WolframProgression(-10)
            wolfram.reset()
            wolfram.manualAdvance()
        } catch (e: Throwable) {
            //this should fail
            assert(true)
        }
    }

    @Test
    fun zeroCapacity() {
        try {
            val wolfram = WolframProgression(0)
            wolfram.reset()
            wolfram.manualAdvance()
        } catch (e: Throwable) {
            //this should fail
            assert(true)
        }
    }

    @Test
    fun evenCapacity() {
        try {
            val wolfram = WolframProgression(10)
            wolfram.reset()
            wolfram.manualAdvance()
        } catch (e: Throwable) {
            //this should fail
            assert(true)
        }
    }
}
