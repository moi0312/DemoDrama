package com.edlo.demodrama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.airbnb.epoxy.EpoxyRecyclerView
import com.edlo.demodrama.databinding.FragmentNestedEpoxyBinding
import com.edlo.demodrama.ui.base.BaseFragment
import com.edlo.demodrama.ui.epoxy.controller.CarouselGameController

class NestedEpoxyFragment : BaseFragment<MainViewModel, FragmentNestedEpoxyBinding>() {
    companion object {
        val TAG = NestedEpoxyFragment::class.java.simpleName
        fun newInstance() = NestedEpoxyFragment()
    }

    private var carouselGameController: CarouselGameController = CarouselGameController()
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
        carouselGameController.onCategoryClicked = {

        }
        carouselGameController.onGameClicked = {
            Toast.makeText(activity, "game $it clicked", Toast.LENGTH_LONG).show()
        }
        recyclerView.setController(carouselGameController)
        recyclerView.requestModelBuild()

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
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(activity as ViewModelStoreOwner).get(MainViewModel::class.java)
    }

    override fun initViewBinding(inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentNestedEpoxyBinding {
        return FragmentNestedEpoxyBinding.inflate(inflater, container, false)
    }
}

