package com.example.tictoccroc.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tictoccroc.R
import com.example.tictoccroc.databinding.ActivitySearchBinding
import com.example.tictoccroc.viewmodels.ClickStationState
import com.example.tictoccroc.viewmodels.SearchViewModel
import com.example.tictoccroc.views.adapters.SubwayStationListAdapter
import com.example.tictoccroc.views.adapters.SubwayStationListType
import com.example.tictoccroc.views.utils.WrapperLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var listAdapter: SubwayStationListAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initLayouts()
        initObserver()
    }

    private fun initLayouts(){
        listAdapter = SubwayStationListAdapter(
            this@SearchActivity,
            SubwayStationListType.SearchResult,
            onClick = { station ->
                viewModel.clickStation(station)
            }
        )

        binding.rvSearchedStation.run {
            layoutManager = WrapperLayoutManager(context, RecyclerView.VERTICAL)
            adapter = listAdapter
        }
    }

    private fun initObserver(){
        viewModel.initEtObservable(binding.etSearchText.textChanges())

        viewModel.searchState.observe(this){ state ->
            when(state){
                is ClickStationState -> finish()
            }
        }

        viewModel.searchResult.observe(this){ list ->
            listAdapter.clearAddAll(list)
        }
    }
}