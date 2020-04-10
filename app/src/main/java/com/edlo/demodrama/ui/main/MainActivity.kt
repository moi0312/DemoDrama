package com.edlo.demodrama.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.ActivityMainBinding
import com.edlo.demodrama.repository.local.Drama
import com.edlo.demodrama.repository.net.NetworkCallback
import com.edlo.demodrama.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getNetworkAvailable().observe(this, Observer { available ->
            setOfflineModeView(available)
        })

        setOfflineModeView(NetworkCallback.INSTANCE.networkAvailable)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    private fun setOfflineModeView(available: Boolean) {
        if(available) {
            binding.txtOfflineMode.visibility = View.GONE
        } else {
            binding.txtOfflineMode.visibility = View.VISIBLE
        }
    }

    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun gotoDramaDetail(drama: Drama) {
        transFragment(DramaDetailFragment.TAG, true, drama)
    }

    private fun transFragment(tag: String, addToBackStack: Boolean = true,  args: Any = Any()) {
        var fragTransaction = supportFragmentManager.beginTransaction()
        var frag: Fragment? = null
        when (tag) {
            MainFragment.TAG -> {
                frag = MainFragment.newInstance()
            }
            DramaDetailFragment.TAG -> {
                frag = DramaDetailFragment.newInstance(args as Drama)
            }
            else -> {
            }
        }
        frag?.let {
            fragTransaction.replace(R.id.container, frag)
            if (addToBackStack) {
                fragTransaction.addToBackStack(tag)
            }
            fragTransaction.commit()
        }
    }
}
