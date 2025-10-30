package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.utils.TypeEquip

data class EquipRetrofitModel(
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

fun EquipRetrofitModel.retrofitModelToEntity(): Equip {
    return Equip(
        id = this.id,
        nro = this.nro,
        codClass = this.codClass,
        descrClass = this.descrClass,
        codTurnEquip = this.codTurnEquip,
        idCheckList = this.idCheckList,
        typeEquip = TypeEquip.entries[this.typeEquip - 1],
        hourMeter = this.hourMeter,
        classify = this.classify,
        flagMechanic = this.flagMechanic != 0,
        flagTire = this.flagTire != 0
    )
}
