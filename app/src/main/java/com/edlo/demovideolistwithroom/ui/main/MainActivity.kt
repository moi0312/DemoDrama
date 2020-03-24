package com.edlo.demovideolistwithroom.ui.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.edlo.demovideolistwithroom.R
import com.edlo.demovideolistwithroom.databinding.ActivityMainBinding
import com.edlo.demovideolistwithroom.db.Drama
import com.edlo.demovideolistwithroom.db.DramaDB
import com.edlo.demovideolistwithroom.net.NetworkCallback
import com.edlo.demovideolistwithroom.net.NetworkCallbackListener
import com.edlo.demovideolistwithroom.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

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

    override fun getActivityViewModel(): MainViewModel {
        return viewModel
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
