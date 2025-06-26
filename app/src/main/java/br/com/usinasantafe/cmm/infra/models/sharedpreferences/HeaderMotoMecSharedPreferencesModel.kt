package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.utils.TypeEquip

data class HeaderMotoMecSharedPreferencesModel(
    var regOperator: Int? = null,
    var idEquip: Int? = null,
    var typeEquip: TypeEquip? = null,
    var idTurn: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var hourMeter: Double? = null,
    var statusCon: Boolean = true
)

fun HeaderMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderMotoMec {
    return with(this) {
        HeaderMotoMec(
            regOperator = regOperator,
            idEquip = idEquip,
            typeEquip = typeEquip,
            idTurn = idTurn,
            nroOS = nroOS,
            idActivity = idActivity,
            hourMeter = hourMeter,
            statusCon = statusCon
        )
    }
}

fun HeaderMotoMec.entityToSharedPreferencesModel(): HeaderMotoMecSharedPreferencesModel {
    return with(this) {
        HeaderMotoMecSharedPreferencesModel(
            regOperator = regOperator,
            idEquip = idEquip,
            typeEquip = typeEquip,
            idTurn = idTurn,
            nroOS = nroOS,
            idActivity = idActivity,
            hourMeter = hourMeter,
            statusCon = statusCon
        )
    }
}