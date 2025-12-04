package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import java.util.Date

data class ConfigSharedPreferencesModel(
    var number: Long? = null,
    var password: String? = null,
    var nroEquip: Long? = null,
    var idEquip: Int? = null,
    var checkMotoMec: Boolean? = null,
    var idServ: Int? = null,
    var version: String? = null,
    var app: String? = null,
    var statusSend: StatusSend = StatusSend.STARTED,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
    var idTurnCheckListLast: Int? = null,
    var dateLastCheckList: Date? = null,
)

fun ConfigSharedPreferencesModel.sharedPreferencesModelToEntity(): Config {
    return with(this) {
        Config(
            password = password,
            number = number,
            nroEquip = nroEquip,
            idEquip = idEquip,
            checkMotoMec = checkMotoMec,
            idServ = idServ,
            version = version,
            app = app,
            statusSend = statusSend,
            flagUpdate = flagUpdate,
            idTurnLastCheckList = idTurnCheckListLast,
            dateLastCheckList = dateLastCheckList
        )
    }
}

fun Config.entityToSharedPreferencesModel(): ConfigSharedPreferencesModel {
    return with(this) {
        ConfigSharedPreferencesModel(
            password = password,
            number = number,
            nroEquip = nroEquip,
            idEquip = idEquip,
            checkMotoMec = checkMotoMec,
            idServ = idServ,
            version = version,
            app = app,
            statusSend = statusSend,
            flagUpdate = flagUpdate,
            idTurnCheckListLast = idTurnLastCheckList,
            dateLastCheckList = dateLastCheckList
        )
    }
}