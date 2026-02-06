package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class ItemMotoMec(
    var id: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    val nroEquipTranshipment: Long? = null,
    val idPressure: Int? = null,
    val idSpeed: Int? = null,
    val idNozzle: Int? = null,
    var statusCon: Boolean? = true,
    var dateHour: Date = Date()
)
