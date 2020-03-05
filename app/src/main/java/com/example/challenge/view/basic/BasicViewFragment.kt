package com.example.challenge.view.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.challenge.R
import com.example.challenge.model.WolframProgression
import com.example.challenge.presenter.BasicPresenter
import kotlinx.android.synthetic.main.fragment_default.*

class BasicViewFragment : Fragment(R.layout.fragment_default), BasicPresenter.ViewListener {

    private val presenter: BasicPresenter = BasicPresenter(this)
    private val adapter = BasicWolframAdapter()

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
        presenter.startProgression()
    }

    override fun onPause() {
        super.onPause()
        presenter.pauseProgression()
    }

    override fun bindNewGeneration(wolfram: WolframProgression) {
        adapter.updateData(wolfram)
    }
}