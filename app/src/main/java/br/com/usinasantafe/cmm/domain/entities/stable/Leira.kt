package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.utils.StatusLeira

data class Leira(
    val idLeira: Int,
    val codLeira: Int,
    val statusLeira: StatusLeira,
)
