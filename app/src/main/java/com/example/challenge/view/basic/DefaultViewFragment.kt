package com.example.challenge.view.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.challenge.R
import com.example.challenge.model.WolframProgression
import com.example.challenge.presenter.DefaultPresenter
import com.example.challenge.view.basic.BasicGridLayoutManager
import com.example.challenge.view.basic.BasicWolframAdapter
import kotlinx.android.synthetic.main.fragment_default.*

class DefaultViewFragment : Fragment(R.layout.fragment_default), DefaultPresenter.ViewListener {

    private val presenter: DefaultPresenter = DefaultPresenter(this)
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