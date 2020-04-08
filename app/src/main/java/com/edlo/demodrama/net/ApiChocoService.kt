package com.edlo.demodrama.net

import com.edlo.demodrama.net.data.DramaResponseData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiChocoService {

    @GET(ApiChoco.GET_DRAMAS_SAMPLE)
    fun listDramasSample(): Deferred<DramaResponseData>?
}