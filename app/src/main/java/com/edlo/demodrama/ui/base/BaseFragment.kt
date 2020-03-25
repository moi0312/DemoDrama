package com.edlo.demodrama.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: BaseViewModel, VDB: ViewBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: VDB

    abstract fun initViewModel(): VM

    abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): VDB

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = initViewBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        binding.lifecycleOwner = activity
        viewModel = initViewModel()
        viewModel.getIsLoading().observe(activity as LifecycleOwner, Observer { isLoading ->
            if(activity is BaseActivity<*>) {
                var base = activity as BaseActivity<*>
                base.getLoadDialog().setIsProgressing(isLoading)
            }
        })
    }

}
