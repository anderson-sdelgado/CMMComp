package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
import br.com.usinasantafe.cmm.utils.TypeOper

data class RFuncaoAtivParadaRetrofitModel(
    val idRFuncaoAtivPar: Int,
    val idAtivPar: Int,
    val funcAtiv: Int,
    val funcParada: Int,
    val tipoFuncao: Int
)

fun RFuncaoAtivParadaRetrofitModel.retrofitModelToEntity(): RFuncaoAtivParada {
    return RFuncaoAtivParada(
        idRFuncaoAtivPar = this.idRFuncaoAtivPar,
        idAtivPar = this.idAtivPar,
        funcAtiv = FuncAtividade.entries[this.funcAtiv],
        funcParada = FuncParada.entries[this.funcParada],
        tipoFuncao = TypeOper.entries[this.tipoFuncao]
    )
}
