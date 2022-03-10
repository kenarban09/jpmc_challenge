package com.krodriguez.jpmorgan.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import com.krodriguez.jpmorgan.databinding.AlbumItemLayoutBinding

class AlbumAdapter(
    private var dataSet: List<RemoteAlbumItem>,
    private val openDetail: (RemoteAlbumItem) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumsViewHolder>() {

    fun updateDataSet(newDataset: List<RemoteAlbumItem>) {
        dataSet = newDataset
        notifyDataSetChanged()
    }

    class AlbumsViewHolder(
        private val binding: AlbumItemLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(dataItem: RemoteAlbumItem, openDetail: (RemoteAlbumItem) -> Unit) {
            binding.tvTitle.text = dataItem.title
            binding.root.setOnClickListener { openDetail(dataItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val itemBinding =
            AlbumItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.onBind(dataSet[position], openDetail)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}