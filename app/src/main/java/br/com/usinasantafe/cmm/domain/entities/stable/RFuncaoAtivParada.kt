package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
import br.com.usinasantafe.cmm.utils.TypeOper

data class RFuncaoAtivParada(
    val idRFuncaoAtivPar: Int,
    val idAtivPar: Int,
    val funcAtiv: FuncAtividade,
    val funcParada: FuncParada,
    val tipoFuncao: TypeOper
)
