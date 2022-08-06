package com.example.tictoccroc.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tictoccroc.R
import com.example.tictoccroc.databinding.ActivityMainBinding
import com.example.tictoccroc.viewmodels.MainViewModel
import com.example.tictoccroc.views.adapters.SubwayStationListAdapter
import com.example.tictoccroc.views.adapters.SubwayStationListType
import com.example.tictoccroc.views.utils.WrapperLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: SubwayStationListAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initLayouts()
        initObserver()

        viewModel.fetch()
    }

    private fun initLayouts(){
        listAdapter = SubwayStationListAdapter(
            this@MainActivity,
            SubwayStationListType.Searched,
            onClickDelete = { station ->
                viewModel.delete(station)
            }
        )

        binding.rvSearchedStation.run {
            layoutManager = FlexboxLayoutManager(context)
            adapter = listAdapter
        }

        binding.vSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.tvDeleteAll.setOnClickListener {
            viewModel.deleteAll()
        }
    }

    private fun initObserver(){
        viewModel.searchResult.observe(this){ list ->
            if(list.isEmpty()){
                listAdapter.clear()
            }else {
                listAdapter.clearAddAll(list)
            }
        }
    }
}