package com.edlo.demovideolistwithroom.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edlo.demogithub.util.GlideApp
import com.edlo.demovideolistwithroom.databinding.ItemDramaBinding
import com.edlo.demovideolistwithroom.db.Drama
import com.edlo.demovideolistwithroom.ui.base.EmptyViewSupportAdapter

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

//        init {
//            itemView.setOnClickListener {
//            }
//        }

        fun bindTo(item: Drama) {
            this.item = item
            binding.txtName.text = item.name
            //TODO: date format
            binding.txtDate.text = item.createdAt

            item.thumb.let {
                GlideApp.with(binding.root.context)
                    .load(it)
                    .into(binding.imgDrama)
            }
        }

    }
}