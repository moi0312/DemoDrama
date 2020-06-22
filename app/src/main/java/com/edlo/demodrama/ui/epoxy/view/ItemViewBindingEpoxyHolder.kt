package com.edlo.demodrama.ui.epoxy.view

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.ItemCategoryHolderBinding
import com.edlo.demodrama.ui.epoxy.helper.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_category_holder)
abstract class ItemViewBindingEpoxyHolder : ViewBindingEpoxyModelWithHolder<ItemCategoryHolderBinding>() {

    @EpoxyAttribute
    lateinit var listener: () -> Unit
    @EpoxyAttribute
    lateinit var title: String

    override fun ItemCategoryHolderBinding.bind() {
        txtTitle.text = this@ItemViewBindingEpoxyHolder.title
        txtTitle.setOnClickListener { listener() }
    }
}