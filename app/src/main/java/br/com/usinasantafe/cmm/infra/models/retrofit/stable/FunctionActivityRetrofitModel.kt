package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.utils.TypeActivity

data class FunctionActivityRetrofitModel(
    val idRFunctionActivity: Int,
    val idActivity: Int,
    val typeActivity: Int,
)

fun FunctionActivityRetrofitModel.retrofitModelToEntity(): FunctionActivity {
    return FunctionActivity(
        idRFunctionActivity = this.idRFunctionActivity,
        idActivity = this.idActivity,
        typeActivity = TypeActivity.entries[this.typeActivity - 1]
    )
}
