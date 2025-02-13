package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.utils.TypeOper

data class MotoMec(
    val idMotoMec: Int,
    val idOperMotoMec: Int,
    val descrOperMotoMec: String,
    val codFuncaoOperMotoMec: Int,
    val posOperMotoMec: Int,
    val tipoOperMotoMec: Int,
    val aplicOperMotoMec: Int,
    val funcaoOperMotoMec: TypeOper,
)
