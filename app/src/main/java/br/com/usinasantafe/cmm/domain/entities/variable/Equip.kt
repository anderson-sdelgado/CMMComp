package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary

data class Equip(
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int? = null,
    val idCheckList: Int? = null,
    val typeEquipMain: TypeEquipMain? = null,
    val typeEquipSecondary: TypeEquipSecondary? = null,
    val hourMeter: Double? = null,
    val classify: Int? = null,
    val flagMechanic: Boolean? = null,
    val flagTire: Boolean? = null,
) {
    init {
        require(id != 0) { "The field 'id' cannot is null." }
        require(nro != 0L) { "The field 'nro' cannot is null." }
        require(codClass != 0) { "The field 'codClass' cannot is null." }
    }
}