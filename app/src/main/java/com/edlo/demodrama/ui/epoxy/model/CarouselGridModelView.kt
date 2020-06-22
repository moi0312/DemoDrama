package com.edlo.demodrama.ui.epoxy.model

import android.content.Context
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.ModelView.Size

@ModelView(saveViewState = true, autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
class CarouselGridModelView(context: Context) : GridCarousel(context) {

    override fun getSnapHelperFactory(): Nothing? = null
}
