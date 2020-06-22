package com.edlo.demodrama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.airbnb.epoxy.EpoxyRecyclerView
import com.edlo.demodrama.databinding.FragmentNestedEpoxyBinding
import com.edlo.demodrama.ui.base.BaseFragment
import com.edlo.demodrama.ui.epoxy.helper.gameCarouselBuilder
import com.edlo.demodrama.ui.epoxy.model.gameCarouselItemView
import com.edlo.demodrama.ui.epoxy.view.itemCarouselGameHolder
import com.edlo.demodrama.ui.epoxy.view.itemCategoryHolder

class NestedEpoxyFragment : BaseFragment<MainViewModel, FragmentNestedEpoxyBinding>() {
    companion object {
        val TAG = NestedEpoxyFragment::class.java.simpleName
        fun newInstance() = NestedEpoxyFragment()
    }

    private lateinit var recyclerView: EpoxyRecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = binding.recyclerView

        initView()
        initList()
        initViewModelObserve()
    }

    private fun initView() {
//        binding.imgClearSearch.setOnClickListener {
//            viewModel.searchDrama("")
//        }
//        binding.inputSearch.setOnEditorActionListener { view, actionId, _ ->
//            when(actionId) {
//                EditorInfo.IME_ACTION_SEARCH -> {
//                    if(activity is BaseActivity<*>) {
//                        (activity as BaseActivity<*>).hideKeyboard(view.windowToken)
//                    }
//                    viewModel.searchDrama(binding.inputSearch.text.toString())
//                    false
//                }
//                else -> false
//            }
//        }
    }

    private fun initList() {
        recyclerView.withModels {

            for (i in 0 until 6) {
                itemCategoryHolder {
                    id("view binding $i")
                    title(" Category $i")
                    listener {
//                        Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG)
//                            .show()
                    }
                }

                gameCarouselBuilder {
                    id("carousel $i")
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        gameCarouselItemView {
                            id("carousel $i-$j")
                            title("Game $j / $lastPage")
                        }
                    }
                }
            }
        }
//        binding.swipeRecyclerView.layoutManager = LinearLayoutManager(activityWeakRef.get())
//
//        binding.swipeRecyclerView.setSwipeMenuCreator { leftMenu, rightMenu, position ->
//            //add swipe menu item
//            var deleteItem = SwipeMenuItem(activityWeakRef.get())
//            deleteItem.setImage(R.drawable.ic_delete)
//            deleteItem.setBackgroundColor(Color.RED)
//            deleteItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
//            deleteItem.setWidth(resources.getDimensionPixelSize(R.dimen.dramaItem_thumb_width))
//
//            rightMenu.addMenuItem(deleteItem)
//        }
//        binding.swipeRecyclerView.setOnItemMenuClickListener { menuBridge, adapterPosition ->
//            menuBridge.closeMenu()
//
//            Log.d("OnItemMenuClick: direction: ${menuBridge.direction}, position: ${menuBridge.position}")
//
//            if(menuBridge.direction == -1) {
//                when(menuBridge.position) {
//                    0 -> {
//                        adapter.removeItem(adapterPosition)
//                    }
//                    else -> {}
//                }
//            }
//        }
//        binding.swipeRecyclerView.useDefaultLoadMore()
////        binding.swipeRecyclerView.setLoadMoreView()
//        binding.swipeRecyclerView.setLoadMoreListener {
//            viewModel.loadMoreDramas()
//        }
//
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.listDramasSample()
//        }
//
//        adapter = DramaAdapter()
//        binding.swipeRecyclerView.adapter = adapter
//        adapter.emptyView = binding.txtListEmpty
    }

    private fun initViewModelObserve() {
//        viewModel.listDramasSample()
//        viewModel.getDramas().observe(activity as LifecycleOwner, Observer {
//            adapter.data = it
//            adapter.notifyDataSetChanged()
//
//            if(binding.swipeRefreshLayout.isRefreshing) {
//                binding.swipeRefreshLayout.isRefreshing = false
//            }
//
//            binding.swipeRecyclerView.loadMoreFinish(adapter.data.isEmpty(), true)
//        })
//        viewModel.getSearchKey().observe(activity as LifecycleOwner, Observer { key ->
//            binding.inputSearch.setText(key)
//            if(key.isNotEmpty()){
//                binding.imgClearSearch.visibility = View.VISIBLE
//            } else {
//                binding.imgClearSearch.visibility = View.GONE
//            }
//        })
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(activity as ViewModelStoreOwner).get(MainViewModel::class.java)
    }

    override fun initViewBinding(inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentNestedEpoxyBinding {
        return FragmentNestedEpoxyBinding.inflate(inflater, container, false)
    }
}

