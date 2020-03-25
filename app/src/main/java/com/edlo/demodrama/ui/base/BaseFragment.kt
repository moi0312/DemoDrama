package com.edlo.demodrama.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseFragment<VM: BaseViewModel, VDB: ViewDataBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: VDB

    abstract fun initViewModel(): VM

    abstract fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): VDB

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = initDataBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = activity
        viewModel = initViewModel()
        viewModel.getIsLoading().observe(activity as LifecycleOwner, Observer { isLoading ->
            if(activity is BaseActivity<*>) {
                var base = activity as BaseActivity<*>
                base.getLoadDialog().setIsProgressing(isLoading)
            }
        })
    }

}
