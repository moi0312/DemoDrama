package com.edlo.demodrama.ui.epoxy.view

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.ItemCarouselGameHolderBinding
import com.edlo.demodrama.ui.epoxy.helper.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_carousel_game_holder)
abstract class ItemCarouselGameHolder : ViewBindingEpoxyModelWithHolder<ItemCarouselGameHolderBinding>() {

    @EpoxyAttribute
    lateinit var listener: () -> Unit
    @EpoxyAttribute
    lateinit var title: String

    override fun ItemCarouselGameHolderBinding.bind() {
        txtGameTitle.text = this@ItemCarouselGameHolder.title
        txtGameTitle.setOnClickListener { listener() }
    }
}