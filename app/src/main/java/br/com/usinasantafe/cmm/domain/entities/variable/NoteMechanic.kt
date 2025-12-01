package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class NoteMechanic(
    val id: Int? = null,
    val idHeader: Int? = null,
    val os: Int? = null,
    val item: Int? = null,
    val dateHourInitial: Date = Date(),
    val dateHourFinish: Date? = null
)
