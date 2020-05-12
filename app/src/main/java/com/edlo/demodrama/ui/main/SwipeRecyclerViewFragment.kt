package com.edlo.demodrama.ui.main

import android.graphics.Color
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.FragmentSwipeRecyclerviewBinding
import com.edlo.demodrama.repository.local.Drama
import com.edlo.demodrama.ui.base.BaseActivity
import com.edlo.demodrama.ui.base.BaseFragment
import com.edlo.demodrama.util.Log
import com.yanzhenjie.recyclerview.SwipeMenu
import com.yanzhenjie.recyclerview.SwipeMenuBridge
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem

class SwipeRecyclerViewFragment : BaseFragment<MainViewModel, FragmentSwipeRecyclerviewBinding>() {
    companion object {
        val TAG = SwipeRecyclerViewFragment::class.java.simpleName
        fun newInstance() = SwipeRecyclerViewFragment()
    }

    private lateinit var adapter: DramaAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        initList()
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

    private fun initList() {
        binding.swipeRecyclerView.layoutManager = LinearLayoutManager(activityWeakRef.get())

        binding.swipeRecyclerView.setSwipeMenuCreator { leftMenu, rightMenu, position ->
            //add swipe menu item
            var deleteItem = SwipeMenuItem(activityWeakRef.get())
            deleteItem.setImage(R.drawable.ic_delete_black_24dp)
            deleteItem.setBackgroundColor(Color.RED)
            deleteItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
            deleteItem.setWidth(resources.getDimensionPixelSize(R.dimen.dramaItem_thumb_width))

            rightMenu.addMenuItem(deleteItem)
        }
        binding.swipeRecyclerView.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()

            Log.d("OnItemMenuClick: direction: ${menuBridge.direction}, position: ${menuBridge.position}")

            if(menuBridge.direction == -1) {
                when(menuBridge.position) {
                    0 -> {
                        adapter.removeItem(adapterPosition)
                    }
                    else -> {}
                }
            }
        }
        binding.swipeRecyclerView.useDefaultLoadMore()
//        binding.swipeRecyclerView.setLoadMoreView()
        binding.swipeRecyclerView.setLoadMoreListener {
            viewModel.loadMoreDramas()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.listDramasSample()
        }

        adapter = DramaAdapter()
        binding.swipeRecyclerView.adapter = adapter
        adapter.emptyView = binding.txtListEmpty
    }

    private fun initViewModelObserve() {
        viewModel.listDramasSample()
        viewModel.getDramas().observe(activity as LifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()

            if(binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }

            binding.swipeRecyclerView.loadMoreFinish(adapter.data.isEmpty(), true)
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
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentSwipeRecyclerviewBinding {
        return FragmentSwipeRecyclerviewBinding.inflate(inflater, container, false)
    }
}

