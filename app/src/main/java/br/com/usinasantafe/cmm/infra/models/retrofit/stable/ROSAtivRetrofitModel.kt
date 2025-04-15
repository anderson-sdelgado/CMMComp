package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSAtiv

data class ROSAtivRetrofitModel(
    val idROSAtiv: Int,
    val idOS: Int,
    val idAtiv: Int,
)

fun ROSAtivRetrofitModel.retrofitModelToEntity(): ROSAtiv {
    return ROSAtiv(
        idROSAtiv = this.idROSAtiv,
        idOS = this.idOS,
        idAtiv = this.idAtiv
    )
}
