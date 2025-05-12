package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity

data class ROSAtivRetrofitModel(
    val idROSAtiv: Int,
    val idOS: Int,
    val idAtiv: Int,
)

fun ROSAtivRetrofitModel.retrofitModelToEntity(): ROSActivity {
    return ROSActivity(
        idROSActivity = this.idROSAtiv,
        idOS = this.idOS,
        idActivity = this.idAtiv
    )
}
