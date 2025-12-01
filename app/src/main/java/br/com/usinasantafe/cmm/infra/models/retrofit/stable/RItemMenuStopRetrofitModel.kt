package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop

data class RItemMenuStopRetrofitModel(
    val id: Int,
    val idFunction: Int,
    val idApp: Int,
    val idStop: Int
)

fun RItemMenuStopRetrofitModel.retrofitModelToEntity(): RItemMenuStop {
    return RItemMenuStop(
        id = this.id,
        idFunction = this.idFunction,
        idApp = this.idApp,
        idStop = this.idStop
    )
}
