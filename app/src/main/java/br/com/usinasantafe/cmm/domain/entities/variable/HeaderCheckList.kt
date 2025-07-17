package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class HeaderCheckList(
    val id: Int? = null,
    val nroEquip: Long,
    val regOperator: Int,
    val nroTurn: Int,
    val dateHour: Date = Date()
)
