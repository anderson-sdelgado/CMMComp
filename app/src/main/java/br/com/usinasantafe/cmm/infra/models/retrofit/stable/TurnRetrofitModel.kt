package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn

data class TurnRetrofitModel(
    val idTurn: Int,
    val codTurnEquip: Int,
    val nroTurn: Int,
    val descrTurn: String,
)

fun TurnRetrofitModel.retrofitModelToEntity(): Turn {
    return Turn(
        idTurn = this.idTurn,
        codTurnEquip = this.codTurnEquip,
        nroTurn = this.nroTurn,
        descrTurn = this.descrTurn
    )
}
