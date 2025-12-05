package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class PreCEC(
    var nroEquip: Int? = null,
    var libEquip: Int? = null,
    var regColab: Long? = null,
    var nroTrailer1: Int? = null,
    var libTrailer1: Int? = null,
    var nroTrailer2: Int? = null,
    var libTrailer2: Int? = null,
    var nroTrailer3: Int? = null,
    var libTrailer3: Int? = null,
    var nroTrailer4: Int? = null,
    var libTrailer4: Int? = null,
    var dateExitMill: Date? = null,
    var dateFieldArrival: Date? = null,
    var dateExitField: Date? = null,
    var nroTurn: Int? = null
)
