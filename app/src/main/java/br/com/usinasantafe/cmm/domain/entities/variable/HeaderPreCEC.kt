package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class HeaderPreCEC(
    var id: Int? = null,
    var nroEquip: Long? = null,
    var releaseEquip: Int? = null,
    var regColab: Long? = null,
    var dateExitMill: Date? = null,
    var dateFieldArrival: Date? = null,
    var dateExitField: Date? = null,
    var nroTurn: Int? = null
)
