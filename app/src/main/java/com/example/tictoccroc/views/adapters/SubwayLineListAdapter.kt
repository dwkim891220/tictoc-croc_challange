package com.example.tictoccroc.views.adapters

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tictoccroc.databinding.ILineBinding
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.views.utils.BaseListAdapter
import com.example.tictoccroc.views.utils.DataBoundViewHolder
import com.example.tictoccroc.views.utils.toPixel

class SubwayLineListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    data: List<SubwayLine>
): BaseListAdapter<SubwayLine>() {
    init {
        addAll(data)
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): DataBoundViewHolder =
        SubwayLineViewHolder(
            lifecycleOwner,
            ILineBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun bind(holder: DataBoundViewHolder, position: Int) {
        when (holder) {
            is SubwayLineViewHolder -> holder.bind(dataList[position])
            else -> throw RuntimeException()
        }
    }

    inner class SubwayLineViewHolder(
        private val lifecycleOwner: LifecycleOwner,
        private val binding: ILineBinding,
    ): DataBoundViewHolder(binding) {
        fun bind(data: SubwayLine?) {
            data?.run {
                binding.lifecycleOwner = lifecycleOwner
                binding.data = this
            }
        }
    }
}

class SubwayLineListDecoration(context: Context?) : RecyclerView.ItemDecoration() {
    private val padding = 8.toPixel(context)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = padding
    }
}