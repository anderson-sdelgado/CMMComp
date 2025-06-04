package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop

data class StopRetrofitModel(
    val idStop: Int,
    val codStop: Int,
    val descrStop: String,
)

fun StopRetrofitModel.retrofitModelToEntity(): Stop {
    return Stop(
        idStop = this.idStop,
        codStop = this.codStop,
        descrStop = this.descrStop
    )
}
