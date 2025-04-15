package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipPneu

data class REquipPneuRetrofitModel(
    val idREquipPneu: Int,
    val idEquip: Int,
    val idPosConfPneu: Int,
    val posPneu: Int,
    val statusPneu: Int,
)

fun REquipPneuRetrofitModel.retrofitModelToEntity(): REquipPneu {
    return REquipPneu(
        idREquipPneu = this.idREquipPneu,
        idEquip = this.idEquip,
        idPosConfPneu = this.idPosConfPneu,
        posPneu = this.posPneu,
        statusPneu = this.statusPneu
    )
}
