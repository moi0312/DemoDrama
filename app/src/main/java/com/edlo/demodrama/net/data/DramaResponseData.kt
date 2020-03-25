package com.edlo.demodrama.net.data

import com.edlo.demodrama.db.Drama
import java.io.Serializable

class DramaResponseData(
    var data: ArrayList<Drama>
): Serializable