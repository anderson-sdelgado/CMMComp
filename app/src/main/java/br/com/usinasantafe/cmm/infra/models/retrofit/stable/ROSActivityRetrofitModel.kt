package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity

data class ROSActivityRetrofitModel(
    val idROSActivity: Int,
    val idOS: Int,
    val idActivity: Int,
)

fun ROSActivityRetrofitModel.retrofitModelToEntity(): ROSActivity {
    return ROSActivity(
        idROSActivity = this.idROSActivity,
        idOS = this.idOS,
        idActivity = this.idActivity
    )
}
