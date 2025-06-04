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
    val hourmeter: Double,
    val measure: Double,
    val type: Int,
    val classify: Int,
    val flagApontMecan: Int,
    val flagApontPneu: Int,
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
        hourmeter = this.hourmeter,
        measure = this.measure,
        type = this.type,
        classify = this.classify,
        flagApontMecan = this.flagApontMecan != 0,
        flagApontPneu = this.flagApontPneu != 0
    )
}
