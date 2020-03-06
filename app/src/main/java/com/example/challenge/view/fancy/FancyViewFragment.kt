package com.example.challenge.view.fancy

import android.os.Bundle
import android.view.View
import com.example.challenge.view.abstracts.AbstractWolframFragment

class FancyViewFragment : AbstractWolframFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.itemAnimator = FancyItemAnimator(presenter.generationCapacity, layoutManager.tileSize)
    }
}