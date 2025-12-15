package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain

data class HeaderMotoMecSharedPreferencesModel(
    var id: Int? = null,
    var regOperator: Int? = null,
    var idEquip: Int? = null,
    var typeEquipMain: TypeEquipMain? = null,
    var idTurn: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var hourMeter: Double? = null,
    var statusCon: Boolean? = null
)

fun HeaderMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderMotoMec {
    return with(this) {
        HeaderMotoMec(
            id = id,
            regOperator = regOperator,
            idEquip = idEquip,
            typeEquipMain = typeEquipMain,
            idTurn = idTurn,
            nroOS = nroOS,
            idActivity = idActivity,
            hourMeter = hourMeter,
            statusCon = statusCon
        )
    }
}

