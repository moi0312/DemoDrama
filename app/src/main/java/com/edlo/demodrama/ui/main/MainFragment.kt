package com.edlo.demodrama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.edlo.demodrama.databinding.FragmentListBinding
import com.edlo.demodrama.ui.base.BaseActivity
import com.edlo.demodrama.ui.base.BaseFragment

class MainFragment : BaseFragment<MainViewModel, FragmentListBinding>() {
    companion object {
        val TAG = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: DramaAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.listView.layoutManager = GridLayoutManager(activity, 3)
        adapter = DramaAdapter()
        adapter.emptyView = binding.txtListEmpty
        binding.listView.adapter = adapter

        initView()
        initViewModelObserve()
    }

    private fun initView() {
        binding.imgClearSearch.setOnClickListener {
            viewModel.searchDrama("")
        }
        binding.inputSearch.setOnEditorActionListener { view, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if(activity is BaseActivity<*>) {
                        (activity as BaseActivity<*>).hideKeyboard(view.windowToken)
                    }
                    viewModel.searchDrama(binding.inputSearch.text.toString())
                    false
                }
                else -> false
            }
        }
    }

    private fun initViewModelObserve() {
        viewModel.listDramasSample()
        viewModel.getDramas().observe(activity as LifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
        viewModel.getSearchKey().observe(activity as LifecycleOwner, Observer { key ->
            binding.inputSearch.setText(key)
            if(key.isNotEmpty()){
                binding.imgClearSearch.visibility = View.VISIBLE
            } else {
                binding.imgClearSearch.visibility = View.GONE
            }
        })
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(activity as ViewModelStoreOwner).get(MainViewModel::class.java)
    }

    override fun initViewBinding(inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }
}

