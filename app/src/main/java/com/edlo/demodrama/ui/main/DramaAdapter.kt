package com.edlo.demodrama.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.ItemDramaBinding
import com.edlo.demodrama.databinding.ItemLoadingViewBinding
import com.edlo.demodrama.repository.local.Drama
import com.edlo.demodrama.ui.base.EmptyViewSupportAdapter
import com.edlo.demodrama.util.GlideApp
import com.edlo.demodrama.util.Utilities


class DramaAdapter : EmptyViewSupportAdapter<DramaAdapter.DramaViewHolder>() {
    companion object {
        var TAG = DramaAdapter::class.java.simpleName
    }

    var data = ArrayList<Drama>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DramaViewHolder {
        return  DramaViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DramaViewHolder, position: Int) {
        holder.bindTo(data[position]!!)
    }

    fun removeItem(adapterPosition: Int) {
        notifyItemRemoved(adapterPosition)
        data.removeAt(adapterPosition)
    }

    class DramaViewHolder(private val binding: ItemDramaBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): DramaViewHolder {
                var binding = ItemDramaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DramaViewHolder(binding)
            }
        }

        private lateinit var item: Drama

        init {
            itemView.setOnClickListener {
                val ctx = itemView.context
                if(ctx is MainActivity){
                    ctx.gotoDramaDetail(item)
                }
            }
        }

        fun bindTo(item: Drama) {
            this.item = item
            binding.txtName.text = item.name
            binding.ratingBar.rating = item.rating
            val ctx = itemView.context
            binding.txtDate.text = Utilities.parseUTCDateString(
                item.createdAt,
                ctx.getString(R.string.dateFormat_yyyyMMdd_T_HHmmssSSS_Z),
                ctx.getString(R.string.dateFormat_yyyyMMdd)
            )

            GlideApp.with(binding.root.context)
                .load(item.thumb)
                .placeholder(R.drawable.shape_drama_thumb)
                .into(binding.imgDrama)
        }

    }
}