package com.example.challenge.view.rave

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.challenge.R
import com.example.challenge.model.WolframProgression
import com.example.challenge.presenter.BasicPresenter
import com.example.challenge.view.shared.BasicGridLayoutManager
import com.example.challenge.view.shared.RippleItemAnimator
import kotlinx.android.synthetic.main.fragment_default.*

class RaveViewFragment : Fragment(R.layout.fragment_default), BasicPresenter.ViewListener {

    private val presenter: BasicPresenter = BasicPresenter(this)
    private val adapter = RaveWolframAdapter()

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
        recycler.itemAnimator =
            RippleItemAnimator(presenter.wolfram.capacity)
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