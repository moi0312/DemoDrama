package com.edlo.demodrama.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edlo.demodrama.util.GlideApp
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.ItemDramaBinding
import com.edlo.demodrama.repository.local.Drama
import com.edlo.demodrama.ui.base.EmptyViewSupportAdapter
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DramaViewHolder {
        return DramaViewHolder.create(parent)
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DramaViewHolder, position: Int) {
        holder.bindTo(data[position])
    }

    class DramaViewHolder: RecyclerView.ViewHolder {
        companion object {
            fun create(parent: ViewGroup): DramaViewHolder {
                var binding = ItemDramaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DramaViewHolder(binding)
            }
        }

        private val binding: ItemDramaBinding
        private lateinit var item: Drama

        constructor(binding: ItemDramaBinding): super(binding.root) {
            this.binding = binding
        }

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