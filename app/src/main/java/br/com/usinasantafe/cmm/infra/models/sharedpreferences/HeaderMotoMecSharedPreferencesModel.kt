package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.lib.TypeEquip

data class HeaderMotoMecSharedPreferencesModel(
    var id: Int? = null,
    var regOperator: Int? = null,
    var idEquip: Int? = null,
    var typeEquip: TypeEquip? = null,
    var idTurn: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var statusCon: Boolean? = true,
    var idEquipMotorPump: Int? = null
)

fun HeaderMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderMotoMec {
    return with(this) {
        HeaderMotoMec(
            id = id,
            regOperator = regOperator,
            idEquip = idEquip,
            typeEquip = typeEquip,
            idTurn = idTurn,
            nroOS = nroOS,
            idActivity = idActivity,
            statusCon = statusCon,
            idEquipMotorPump = idEquipMotorPump
        )
    }
}

