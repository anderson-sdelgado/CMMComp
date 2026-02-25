package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Service

data class ServiceRetrofitModel(
    val id: Int,
    val cod: Int,
    val descr: String
)

fun ServiceRetrofitModel.retrofitModelToEntity(): Service {
    return Service(
        id = this.id,
        cod = this.cod,
        descr = this.descr
    )
}
