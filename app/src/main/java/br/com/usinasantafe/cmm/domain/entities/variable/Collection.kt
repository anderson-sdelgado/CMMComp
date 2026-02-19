package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class Collection(
    val id: Int,
    val idHeader: Int,
    val nroOS: Int,
    var value: Double? = null,
    val dateHour: Date = Date()
)
