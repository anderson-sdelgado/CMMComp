package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity

data class ROSActivityRetrofitModel(
    val idOS: Int,
    val idActivity: Int,
)

fun ROSActivityRetrofitModel.retrofitModelToEntity(): ROSActivity {
    return ROSActivity(
        idOS = this.idOS,
        idActivity = this.idActivity
    )
}
