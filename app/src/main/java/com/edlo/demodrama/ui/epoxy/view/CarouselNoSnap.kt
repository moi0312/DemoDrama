package com.edlo.demodrama.ui.epoxy.view

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.ModelView.Size

@ModelView(saveViewState = true, autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
class CarouselNoSnap(context: Context) : GridCarousel(context) {

    override fun getSnapHelperFactory(): Nothing? = null
}
