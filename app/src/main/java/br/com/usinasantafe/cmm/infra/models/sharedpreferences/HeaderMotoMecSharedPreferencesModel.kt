package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec

data class HeaderMotoMecSharedPreferencesModel(
    var id: Long? = null,
    var regOperator: Int? = null,
    var idEquip: Int? = null,
    var idTurn: Int? = null,
    var nroOS: Int? = null
)

fun HeaderMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderMotoMec {
    return with(this) {
        HeaderMotoMec(
            id = id,
            regOperator = regOperator,
            idEquip = idEquip,
            idTurn = idTurn,
            nroOS = nroOS
        )
    }
}

fun HeaderMotoMec.entityToSharedPreferencesModel(): HeaderMotoMecSharedPreferencesModel {
    return with(this) {
        HeaderMotoMecSharedPreferencesModel(
            id = id,
            regOperator = regOperator,
            idEquip = idEquip,
            idTurn = idTurn,
            nroOS = nroOS
        )
    }
}