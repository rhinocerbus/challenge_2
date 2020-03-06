package com.example.challenge.view.abstracts
//TIL you can name a package "abstract" without blowing up the kotlin -> java compiler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.challenge.R
import com.example.challenge.presenter.BasicPresenter
import com.example.challenge.view.shared.BasicGridLayoutManager
import com.example.challenge.view.shared.BasicWolframAdapter

abstract class AbstractWolframFragment(viewId: Int = R.layout.fragment_default) : Fragment(viewId), BasicPresenter.ViewListener {

    @BindView(R.id.recycler) lateinit var recycler: RecyclerView

    private var active = false
    val presenter by lazy { buildPresenter() }
    private val adapter by lazy { buildAdapter() }
    val layoutManager by lazy {
        BasicGridLayoutManager(requireContext(), presenter.generationCapacity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)

        adapter.updateData(presenter)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
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

    open fun buildPresenter(): BasicPresenter {
        return BasicPresenter(this)
    }

    open fun buildAdapter(): BasicWolframAdapter {
        return BasicWolframAdapter()
    }

    override fun bindNewGeneration(updatedPresenter: BasicPresenter) {
        adapter.updateData(updatedPresenter)
    }
}