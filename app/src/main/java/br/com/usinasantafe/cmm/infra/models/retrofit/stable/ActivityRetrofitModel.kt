package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity

data class ActivityRetrofitModel(
    val idActivity: Int,
    val codActivity: Int,
    val descrActivity: String
)

fun ActivityRetrofitModel.retrofitModelToEntity(): Activity {
    return Activity(
        idActivity = this.idActivity,
        codActivity = this.codActivity,
        descrActivity = this.descrActivity
    )
}
