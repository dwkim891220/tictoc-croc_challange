package com.example.tictoccroc.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tictoccroc.databinding.*
import com.example.tictoccroc.models.server.SubwayStation
import com.example.tictoccroc.views.utils.BaseListAdapter
import com.example.tictoccroc.views.utils.DataBoundViewHolder
import com.example.tictoccroc.views.utils.WrapperLayoutManager

class SubwayStationListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val type: SubwayStationListType,
    private val onClick: ((SubwayStation) -> Unit)? = null,
    private val onClickDelete: ((SubwayStation) -> Unit)? = null,
): BaseListAdapter<SubwayStation>() {
    override fun getItemViewType(position: Int): Int =
        when(type){
            SubwayStationListType.Searched -> SubwayStationListType.Searched.ordinal
            SubwayStationListType.SearchResult -> SubwayStationListType.SearchResult.ordinal
        }

    override fun createBinding(parent: ViewGroup, viewType: Int): DataBoundViewHolder =
        when(viewType){
            SubwayStationListType.Searched.ordinal ->
                SearchedStationViewHolder(
                    lifecycleOwner,
                    ISearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onClickDelete
                )
            SubwayStationListType.SearchResult.ordinal ->
                SubwayStationViewHolder(
                    lifecycleOwner,
                    ISearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onClick
                )
            else -> throw RuntimeException()
        }


    override fun bind(holder: DataBoundViewHolder, position: Int) {
        when (holder) {
            is SearchedStationViewHolder -> holder.bind(dataList[position])
            is SubwayStationViewHolder -> holder.bind(dataList[position])
            else -> throw RuntimeException()
        }
    }

    inner class SubwayStationViewHolder(
        private val lifecycleOwner: LifecycleOwner,
        private val binding: ISearchResultBinding,
        private val onClick: ((SubwayStation) -> Unit)?
    ): DataBoundViewHolder(binding) { fun bind(data: SubwayStation?) {
            data?.run {
                binding.lifecycleOwner = lifecycleOwner
                binding.data = this

                binding.rvSubwayLines.run {
                    layoutManager = WrapperLayoutManager(context, RecyclerView.HORIZONTAL)
                    addItemDecoration(SubwayLineListDecoration(context))
                    adapter = SubwayLineListAdapter(lifecycleOwner, data.subwayLines!!)
                }

                binding.parent.setOnClickListener {
                    onClick?.invoke(data)
                }
            }
        }
    }

    inner class SearchedStationViewHolder(
        private val lifecycleOwner: LifecycleOwner,
        private val binding: ISearchedBinding,
        private val onClickDelete: ((SubwayStation) -> Unit)?
    ): DataBoundViewHolder(binding) {
        fun bind(data: SubwayStation?) {
            data?.run {
                binding.lifecycleOwner = lifecycleOwner
                binding.data = this

                binding.rvSubwayLines.run {
                    layoutManager = WrapperLayoutManager(context, RecyclerView.HORIZONTAL)
                    addItemDecoration(SubwayLineListDecoration(context))
                    adapter = SubwayLineListAdapter(lifecycleOwner, data.subwayLines!!)
                }

                binding.ivDelete.setOnClickListener{
                    onClickDelete?.invoke(data)
                }
            }
        }
    }
}

enum class SubwayStationListType {
    Searched,
    SearchResult
}