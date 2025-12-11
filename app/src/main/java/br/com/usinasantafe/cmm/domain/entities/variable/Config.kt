package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import java.util.Date

data class Config(
    var number: Long? = null,
    val nroEquip: Long? = null,
    var password: String? = null,
    var checkMotoMec: Boolean? = true,
    var idServ: Int? = null,
    var version: String? = null,
    var app: String? = null,
    var statusSend: StatusSend = StatusSend.STARTED,
    val flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    val equip: Equip? = null,
    var idTurnLastCheckList: Int? = null,
    var dateLastCheckList: Date? = null,
)
