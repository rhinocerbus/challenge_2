package com.example.challenge.view.rave

import android.os.Bundle
import android.view.View
import com.example.challenge.view.abstracts.AbstractWolframFragment
import com.example.challenge.view.shared.BasicWolframAdapter
import com.example.challenge.view.shared.RippleItemAnimator

class RaveViewFragment : AbstractWolframFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.itemAnimator =
            RippleItemAnimator(presenter.generationCapacity)
    }

    override fun buildAdapter(): BasicWolframAdapter {
        return RaveWolframAdapter()
    }
}