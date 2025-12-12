package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary

data class EquipMainRetrofitModel(
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val typeEquip: Int,
)

fun EquipMainRetrofitModel.retrofitModelToEntity(): Equip {
    return Equip(
        id = this.id,
        nro = this.nro,
        codClass = this.codClass,
        descrClass = this.descrClass,
        typeEquipSecondary = TypeEquipSecondary.entries[this.typeEquip - 1],
    )
}

data class EquipSecondaryRetrofitModel(
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeEquip: Int,
    val hourMeter: Double,
    val classify: Int,
    val flagMechanic: Int,
    val flagTire: Int
)

fun EquipSecondaryRetrofitModel.retrofitModelToEntity(): Equip {
    return Equip(
        id = this.id,
        nro = this.nro,
        codClass = this.codClass,
        descrClass = this.descrClass,
        typeEquipMain = TypeEquipMain.entries[this.typeEquip - 1],
        codTurnEquip = this.codTurnEquip,
        idCheckList = this.idCheckList,
        hourMeter = this.hourMeter,
        classify = this.classify,
        flagMechanic = this.flagMechanic != 0,
        flagTire = this.flagTire != 0
    )
}