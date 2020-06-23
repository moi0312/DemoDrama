package com.edlo.demodrama.ui.epoxy.helper
//
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.edlo.demodrama.ui.epoxy.model.CarouselGridModelViewModelBuilder
import com.edlo.demodrama.ui.epoxy.model.CarouselGridModelViewModel_

//
///**
// * Example that illustrate how to add a builder for nested list (ex: carousel) that allow building
// * it using DSL :
// *
// *   carouselBuilder {
// *     id(...)
// *     for (...) {
// *       carouselItemCustomView {
// *         id(...)
// *       }
// *     }
// *   }
// *
// * @link https://github.com/airbnb/epoxy/issues/847
// */

fun ModelCollector.gameCarouselBuilder(builder: GameCarouselBuilder.() -> Unit): CarouselGridModelViewModel_ {
    val carouselBuilder = GameCarouselBuilder().apply { builder() }
    add(carouselBuilder.carouselGridModel)
    return carouselBuilder.carouselGridModel
}

class GameCarouselBuilder (
    internal val carouselGridModel: CarouselGridModelViewModel_ = CarouselGridModelViewModel_()
) : ModelCollector, CarouselGridModelViewModelBuilder by carouselGridModel {
    private val models = mutableListOf<EpoxyModel<*>>()
//
    override fun add(model: EpoxyModel<*>) {
        models.add(model)

        // Set models list every time a model is added so that it can run debug validations to
        // ensure it is still valid to mutate the carousel model.
        carouselGridModel.models(models)
    }
}
