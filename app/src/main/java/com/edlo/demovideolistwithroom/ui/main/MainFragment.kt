package com.edlo.demovideolistwithroom.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edlo.demovideolistwithroom.R
import com.edlo.demovideolistwithroom.databinding.FragmentListBinding
import com.edlo.demovideolistwithroom.ui.base.BaseFragment

class MainFragment : BaseFragment<MainViewModel, FragmentListBinding>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: DramaAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.listView.layoutManager = GridLayoutManager(activity, 3)
        adapter = DramaAdapter()
        adapter.emptyView = binding.txtListEmpty
        binding.listView.adapter = adapter

        viewModel.listDramasSample()
        viewModel.getDramas().observe(activity as LifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }

    override fun initViewModel(): MainViewModel =
        ViewModelProvider(activity as ViewModelStoreOwner).get(MainViewModel::class.java)

    override fun initDataBinding(inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentListBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
    }
}
