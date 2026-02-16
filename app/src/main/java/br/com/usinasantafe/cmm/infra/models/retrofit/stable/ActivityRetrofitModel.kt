package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity

data class ActivityRetrofitModel(
    val id: Int,
    val cod: Int,
    val descr: String
)

fun ActivityRetrofitModel.retrofitModelToEntity(): Activity {
    return Activity(
        id = this.id,
        cod = this.cod,
        descr = this.descr
    )
}
