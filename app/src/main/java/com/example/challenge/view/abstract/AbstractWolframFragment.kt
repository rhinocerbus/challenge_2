package com.example.challenge.view.abstract

import androidx.fragment.app.Fragment
import com.example.challenge.R
import com.example.challenge.presenter.AbstractWolframPresenter
import com.example.challenge.presenter.BasicPresenter

abstract class AbstractWolframFragment(viewId: Int) : Fragment(viewId) {
    private var active = false

    lateinit var presenter: AbstractWolframPresenter

    override fun onResume() {
        super.onResume()
        if(active)
            presenter.startProgression()
    }

    override fun onPause() {
        super.onPause()
        if(active)
            presenter.pauseProgression()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        active = isVisibleToUser
        if(isVisibleToUser) onResume() else onPause()
    }
}