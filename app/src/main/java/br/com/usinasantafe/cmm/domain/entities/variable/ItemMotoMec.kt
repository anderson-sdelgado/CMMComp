package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class ItemMotoMec(
    var id: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    val nroEquipTranshipment: Long? = null,
    var statusCon: Boolean? = false,
    var dateHour: Date = Date()
)