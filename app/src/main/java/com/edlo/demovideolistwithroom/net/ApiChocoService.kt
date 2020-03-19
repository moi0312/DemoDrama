package com.example.testcoroutines.net

import com.edlo.demovideolistwithroom.db.Drama
import com.edlo.demovideolistwithroom.net.data.DramaResponseData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiChocoService {

    @GET(ApiChoco.GET_DRAMAS_SAMPLE)
    fun listDramasSample(): Deferred<DramaResponseData>?
}