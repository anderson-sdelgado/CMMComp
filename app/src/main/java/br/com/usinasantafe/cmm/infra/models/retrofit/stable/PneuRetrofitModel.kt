package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Pneu

data class PneuRetrofitModel(
    val idPneu: Int,
    val nroPneu: Int,
)

fun PneuRetrofitModel.retrofitModelToEntity(): Pneu {
    return Pneu(
        idPneu = this.idPneu,
        nroPneu = this.nroPneu
    )
}
