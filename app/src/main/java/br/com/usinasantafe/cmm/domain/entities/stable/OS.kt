package br.com.usinasantafe.cmm.domain.entities.stable

data class OS(
    val id: Int,
    val nro: Int,
    val idRelease: Int? = null,
    val idPropAgr: Int? = null,
    val area: Double? = null,
)
