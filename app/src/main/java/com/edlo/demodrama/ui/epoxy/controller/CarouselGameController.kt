package com.edlo.demodrama.ui.epoxy.controller

import android.widget.Toast
import com.airbnb.epoxy.EpoxyController
import com.edlo.demodrama.ui.epoxy.helper.gameCarouselBuilder
import com.edlo.demodrama.ui.epoxy.view.itemCarouselGameHolder
import com.edlo.demodrama.ui.epoxy.view.itemCategoryHolder

class CarouselGameController : EpoxyController() {
    private var currentCategory: Int = 0
    var onCategoryClicked : ((Int)->Unit)? = null
    var onGameClicked : ((Int)->Unit)? = null

    override fun buildModels() {
        for (i in 0 until 6) {
            itemCategoryHolder {
                id("view binding $i")
                title(" Category $i")
                listener {
                    currentCategory = i
                    onCategoryClicked?.let { it(i) }
                    requestModelBuild()
                }
            }

            if(currentCategory == i) {

                gameCarouselBuilder {
                    id("carousel $i")
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        itemCarouselGameHolder {
                            id("carousel $i-$j")
                            title("Game $j / $lastPage")
                            listener {
                                onGameClicked?.let { it(i) }
                            }
                        }
                    }
                }

            }
        }
    }
}