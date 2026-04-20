package br.com.usinasantafe.cmm.domain.entities.stable

data class OS(
    val idOS: Int,
    val nroOS: Int,
    val idReleaseOS: Int? = null,
    val idPropAgr: Int? = null,
    val areaOS: Double? = null,
)
