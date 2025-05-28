package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec

data class HeaderMotoMecSharedPreferencesModel(
    var regOperator: Int? = null,
    var idEquip: Int? = null,
    var idTurn: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var measureInitial: Double? = null
)

fun HeaderMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderMotoMec {
    return with(this) {
        HeaderMotoMec(
            regOperator = regOperator,
            idEquip = idEquip,
            idTurn = idTurn,
            nroOS = nroOS,
            idActivity = idActivity,
            measureInitial = measureInitial
        )
    }
}

fun HeaderMotoMec.entityToSharedPreferencesModel(): HeaderMotoMecSharedPreferencesModel {
    return with(this) {
        HeaderMotoMecSharedPreferencesModel(
            regOperator = regOperator,
            idEquip = idEquip,
            idTurn = idTurn,
            nroOS = nroOS,
            idActivity = idActivity,
            measureInitial = measureInitial
        )
    }
}