package com.example.challenge.view.fancy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.challenge.R
import com.example.challenge.presenter.BasicPresenter
import com.example.challenge.view.abstract.AbstractWolframFragment
import com.example.challenge.view.rave.RaveWolframAdapter
import com.example.challenge.view.shared.BasicGridLayoutManager
import com.example.challenge.view.shared.BasicWolframAdapter
import kotlinx.android.synthetic.main.fragment_default.*

class FancyViewFragment : Fragment(R.layout.fragment_default), BasicPresenter.ViewListener {

    @BindView(R.id.recycler) lateinit var recycler: RecyclerView
    private var active = false
    lateinit var adapter: BasicWolframAdapter
    private lateinit var layoutManager: BasicGridLayoutManager

    val presenter: BasicPresenter = BasicPresenter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)

        layoutManager =  BasicGridLayoutManager(
            requireContext(),
            presenter.generationCapacity
        )

        adapter = BasicWolframAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = FancyItemAnimator(presenter.generationCapacity, layoutManager.tileSize)

        adapter.updateData(presenter)
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

    override fun bindNewGeneration(updatedPresenter: BasicPresenter) {
        adapter.updateData(updatedPresenter)
    }
}