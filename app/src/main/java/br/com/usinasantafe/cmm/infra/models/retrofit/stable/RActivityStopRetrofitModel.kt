package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop

data class RActivityStopRetrofitModel(
    val idActivity: Int,
    val idStop: Int,
)

fun RActivityStopRetrofitModel.retrofitModelToEntity(): RActivityStop {
    return RActivityStop(
        idActivity = this.idActivity,
        idStop = this.idStop
    )
}
