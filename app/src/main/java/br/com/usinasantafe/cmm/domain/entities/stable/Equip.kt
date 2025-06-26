package br.com.usinasantafe.cmm.domain.entities.stable

data class Equip(
    val idEquip: Int,
    val nroEquip: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeFert: Int,
    val hourMeter: Double,
    val classify: Int,
    val flagMechanic: Boolean,
) {
    init {
        require(idEquip != 0) { "The field 'idEquip' cannot is null." }
        require(nroEquip != 0L) { "The field 'nroEquip' cannot is null." }
        require(codClass != 0) { "The field 'codClass' cannot is null." }
        require(codTurnEquip != 0) { "The field 'codTurnEquip' cannot is null." }
        require(classify != 0) { "The field 'classify' cannot is null." }
    }
}

