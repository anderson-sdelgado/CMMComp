package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.MotoMec
import br.com.usinasantafe.cmm.utils.TypeOper

data class MotoMecRetrofitModel(
    val idMotoMec: Int,
    val idOperMotoMec: Int,
    val descrOperMotoMec: String,
    val codFuncaoOperMotoMec: Int,
    val posOperMotoMec: Int,
    val tipoOperMotoMec: Int,
    val aplicOperMotoMec: Int,
    val funcaoOperMotoMec: Int
)

fun MotoMecRetrofitModel.retrofitModelToEntity(): MotoMec {
    return MotoMec(
        idMotoMec = this.idMotoMec,
        idOperMotoMec = this.idOperMotoMec,
        descrOperMotoMec = this.descrOperMotoMec,
        codFuncaoOperMotoMec = this.codFuncaoOperMotoMec,
        posOperMotoMec = this.posOperMotoMec,
        tipoOperMotoMec = this.tipoOperMotoMec,
        aplicOperMotoMec = this.aplicOperMotoMec,
        funcaoOperMotoMec = TypeOper.entries[this.funcaoOperMotoMec]
    )
}
