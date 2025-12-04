package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.lib.TypeStop

data class FunctionStop(
    val idFunctionStop: Int,
    val idStop: Int,
    val typeStop: TypeStop
)
