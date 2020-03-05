package com.example.challenge.view.chonk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.challenge.R
import com.example.challenge.model.WolframProgression
import com.example.challenge.presenter.ChonkPresenter
import com.example.challenge.view.shared.BasicGridLayoutManager
import com.example.challenge.view.shared.BasicWolframAdapter
import kotlinx.android.synthetic.main.fragment_default.*


class ChonkViewFragment : Fragment(R.layout.fragment_default), ChonkPresenter.ViewListener {

    private val presenter: ChonkPresenter = ChonkPresenter(this)
    private val adapter = BasicWolframAdapter()

    private var active = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.updateData(presenter.wolfram)
        recycler.adapter = adapter
        recycler.layoutManager =
            BasicGridLayoutManager(
                requireContext(),
                presenter.wolfram.capacity
            )
    }

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

    override fun bindNewGeneration(wolfram: WolframProgression) {
        adapter.updateData(wolfram)
    }
}