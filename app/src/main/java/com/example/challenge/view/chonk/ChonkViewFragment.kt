package com.example.challenge.view.chonk

import com.example.challenge.presenter.BasicPresenter
import com.example.challenge.view.abstracts.AbstractWolframFragment


class ChonkViewFragment : AbstractWolframFragment() {

    override fun buildPresenter(): BasicPresenter {
        return BasicPresenter(this, 65)
    }
}