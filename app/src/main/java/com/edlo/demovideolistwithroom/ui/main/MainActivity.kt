package com.edlo.demovideolistwithroom.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.edlo.demovideolistwithroom.R
import com.edlo.demovideolistwithroom.databinding.ActivityMainBinding
import com.edlo.demovideolistwithroom.db.DramaDB
import com.edlo.demovideolistwithroom.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun initViewModel(): MainViewModel? {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun getActivityViewModel(): MainViewModel? {
        return viewModel
    }

    fun createDB() {
        val db = Room.databaseBuilder( applicationContext,
            DramaDB::class.java, DramaDB.DB_NAME
        ).build()
    }
}
