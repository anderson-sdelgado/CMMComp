package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.lib.OptionRespCheckList

data class RespItemCheckList(
    val id: Int? = null,
    val idItem: Int,
    val option: OptionRespCheckList,
)
