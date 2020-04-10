package com.edlo.demodrama.repository.net

import com.edlo.demodrama.repository.net.response.DramaResponseData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiChocoService {

    @GET(ApiChoco.GET_DRAMAS_SAMPLE)
    fun listDramasSample(): Deferred<DramaResponseData>?
}