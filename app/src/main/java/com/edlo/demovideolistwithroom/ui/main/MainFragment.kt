package com.edlo.demovideolistwithroom.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.edlo.demovideolistwithroom.R
import com.edlo.demovideolistwithroom.databinding.FragmentListBinding
import com.edlo.demovideolistwithroom.ui.base.BaseActivity
import com.edlo.demovideolistwithroom.ui.base.BaseFragment
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
        return if(activity is MainActivity) {
            (activity as MainActivity).getActivityViewModel()
        } else {
            ViewModelProvider(activity as ViewModelStoreOwner).get(MainViewModel::class.java)
        }
    }

    override fun initDataBinding(inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentListBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
    }
}

