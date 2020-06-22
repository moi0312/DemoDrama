package com.edlo.demodrama.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.ItemDramaBinding
import com.edlo.demodrama.repository.local.Drama
import com.edlo.demodrama.ui.base.EmptyViewSupportAdapter
import com.edlo.demodrama.util.GlideApp
import com.edlo.demodrama.util.Utilities
import kotlinx.android.extensions.LayoutContainer


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
        holder.bind(data[position]!!)
    }

    fun removeItem(adapterPosition: Int) {
        notifyItemRemoved(adapterPosition)
        data.removeAt(adapterPosition)
    }

    class DramaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer {
        companion object {
            fun create(parent: ViewGroup): DramaViewHolder {
                var binding = ItemDramaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DramaViewHolder(binding.root)
            }
        }

        override val containerView: View?
            get() = itemView

        private lateinit var item: Drama

        init {
            itemView.setOnClickListener {
                val ctx = containerView!!.context
                if(ctx is MainActivity){
                    ctx.gotoDramaDetail(item)
                }
            }
        }

        fun bind(item: Drama) {
            this.item = item
//            txtName.text = item.name
//            ratingBar.rating = item.rating
//            val ctx = itemView.context
//            txtDate.text = Utilities.parseUTCDateString(
//                item.createdAt,
//                ctx.getString(R.string.dateFormat_yyyyMMdd_T_HHmmssSSS_Z),
//                ctx.getString(R.string.dateFormat_yyyyMMdd)
//            )

//            GlideApp.with(containerView!!.context)
//                .load(item.thumb)
//                .placeholder(R.drawable.shape_drama_thumb)
//                .into(imgDrama)
        }

    }
}