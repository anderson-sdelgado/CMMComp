package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.utils.TypeStop

data class FunctionStopRetrofitModel(
    val idRFunctionStop: Int,
    val idStop: Int,
    val typeStop: Int
)

fun FunctionStopRetrofitModel.retrofitModelToEntity(): FunctionStop {
    return FunctionStop(
        idRFunctionStop = this.idRFunctionStop,
        idStop = this.idStop,
        typeStop = TypeStop.entries[this.typeStop - 1]
    )
}