package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip

data class EquipRetrofitModel(
    val idEquip: Int,
    val nroEquip: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeFert: Int,
    val hourMeter: Double,
    val classify: Int,
    val flagMechanic: Int,
)

fun EquipRetrofitModel.retrofitModelToEntity(): Equip {
    return Equip(
        idEquip = this.idEquip,
        nroEquip = this.nroEquip,
        codClass = this.codClass,
        descrClass = this.descrClass,
        codTurnEquip = this.codTurnEquip,
        idCheckList = this.idCheckList,
        typeFert = this.typeFert,
        hourMeter = this.hourMeter,
        classify = this.classify,
        flagMechanic = this.flagMechanic != 0
    )
}
