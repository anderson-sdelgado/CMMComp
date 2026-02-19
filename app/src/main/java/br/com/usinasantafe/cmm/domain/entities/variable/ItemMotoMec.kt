package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class ItemMotoMec(
    var id: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    var nroEquipTranshipment: Long? = null,
    var idPressure: Int? = null,
    var idSpeed: Int? = null,
    var idNozzle: Int? = null,
    var valuePressure: Double? = null,
    var speedPressure: Int? = null,
    var statusCon: Boolean? = true,
    var dateHour: Date = Date()
)
