package com.edlo.demodrama.repository.net.response

import com.edlo.demodrama.repository.local.Drama
import java.io.Serializable

class DramaResponseData(
    var data: ArrayList<Drama>
): Serializable