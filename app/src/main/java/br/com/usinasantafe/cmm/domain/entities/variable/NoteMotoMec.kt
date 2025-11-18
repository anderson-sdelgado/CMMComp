package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class NoteMotoMec(
    var id: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    val idEquipTrans: Int? = null,
    var statusCon: Boolean? = true,
    var dateHour: Date = Date()
)