package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.StatusSend

data class Config(
    var number: Long? = null,
    val nroEquip: Long? = null,
    var password: String? = null,
    var checkMotoMec: Boolean? = true,
    var idBD: Int? = null,
    var version: String? = null,
    var app: String? = null,
    var statusSend: StatusSend = StatusSend.STARTED,
    val flagUpdate: FlagUpdate = FlagUpdate.OUTDATED
)
