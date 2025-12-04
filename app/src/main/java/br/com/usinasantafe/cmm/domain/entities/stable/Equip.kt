package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.lib.TypeEquip

data class Equip(
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeEquip: TypeEquip,
    val hourMeter: Double,
    val classify: Int,
    val flagMechanic: Boolean,
    val flagTire: Boolean,
) {
    init {
        require(id != 0) { "The field 'id' cannot is null." }
        require(nro != 0L) { "The field 'nro' cannot is null." }
        require(codClass != 0) { "The field 'codClass' cannot is null." }
        require(codTurnEquip != 0) { "The field 'codTurnEquip' cannot is null." }
        require(classify != 0) { "The field 'classify' cannot is null." }
    }
}

