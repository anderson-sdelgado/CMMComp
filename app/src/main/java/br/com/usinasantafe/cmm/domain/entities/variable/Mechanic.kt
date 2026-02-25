package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class Mechanic(
    val id: Int? = null,
    val idHeader: Int? = null,
    val nroOS: Int? = null,
    val seqItem: Int? = null,
    val dateHourInitial: Date = Date(),
    val dateHourFinish: Date? = null
)
