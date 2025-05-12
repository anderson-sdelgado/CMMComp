package br.com.usinasantafe.cmm.domain.entities.stable

data class Equip(
    val idEquip: Int,
    val nroEquip: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeFert: Int,
    val hourmeter: Double,
    val measurement: Double,
    val type: Int,
    val classify: Int,
    val flagApontMecan: Boolean,
    val flagApontPneu: Boolean,
) {
    init {
        require(idEquip != 0) { "The field 'idEquip' cannot is null." }
        require(nroEquip != 0L) { "The field 'nroEquip' cannot is null." }
        require(codClass != 0) { "The field 'codClass' cannot is null." }
        require(codTurnEquip != 0) { "The field 'codTurnEquip' cannot is null." }
        require(idCheckList != 0) { "The field 'idCheckList' cannot is null." }
        require(typeFert != 0) { "The field 'typeFert' cannot is null." }
        require(type != 0) { "The field 'type' cannot is null." }
        require(classify != 0) { "The field 'classify' cannot is null." }
    }
}

