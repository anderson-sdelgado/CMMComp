package br.com.usinasantafe.cmm.domain.entities.variable

import java.util.Date

data class PreCEC(
    val nroEquip: Int? = null,
    val libEquip: Int? = null,
    val regColab: Long? = null,
    val nroTrailer1: Int? = null,
    val libTrailer1: Int? = null,
    val nroTrailer2: Int? = null,
    val libTrailer2: Int? = null,
    val nroTrailer3: Int? = null,
    val libTrailer3: Int? = null,
    val nroTrailer4: Int? = null,
    val libTrailer4: Int? = null,
    val dateFieldArrival: Date? = null,
    val dateFieldExit: Date? = null,
    val dateMillExit: Date? = null,
    val nroTurn: Int? = null
)
