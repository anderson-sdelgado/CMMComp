package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop

data class RActivityStopRetrofitModel(
    val idRActivityStop: Int,
    val idActivity: Int,
    val idStop: Int,
)

fun RActivityStopRetrofitModel.retrofitModelToEntity(): RActivityStop {
    return RActivityStop(
        idRActivityStop = this.idRActivityStop,
        idActivity = this.idActivity,
        idStop = this.idStop
    )
}
