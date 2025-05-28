package br.com.usinasantafe.cmm.domain.entities.stable

data class OS(
    val idOS: Int,
    val nroOS: Int,
    val idLibOS: Int,
    val idPropAgr: Int,
    val areaOS: Double,
    val typeOS: Int,
    val idEquip: Int,
) {
    init {
        require(idOS != 0) { "The field 'idOS' cannot is null." }
        require(nroOS != 0) { "The field 'nroOS' cannot is null." }
        require(idLibOS != 0) { "The field 'idLibOS' cannot is null." }
        require(idPropAgr != 0) { "The field 'idPropAgr' cannot is null." }
        require(typeOS != 0) { "The field 'typeOS' cannot is null." }
        require(idEquip != 0) { "The field 'idEquip' cannot is null." }
    }
}
