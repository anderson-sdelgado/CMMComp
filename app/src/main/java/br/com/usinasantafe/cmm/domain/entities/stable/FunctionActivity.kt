package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.lib.TypeActivity

data class FunctionActivity(
    val idFunctionActivity: Int,
    val idActivity: Int,
    val typeActivity: TypeActivity,
)
