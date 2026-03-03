package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class Mechanic(
    var id: Int? = null,
    var idHeader: Int? = null,
    var nroOS: Int? = null,
    var seqItem: Int? = null,
    var dateHourInitial: Date = Date(),
    var dateHourFinish: Date? = null
)
