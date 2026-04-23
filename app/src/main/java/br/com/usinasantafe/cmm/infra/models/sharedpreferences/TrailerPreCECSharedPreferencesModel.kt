package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.TrailerPreCEC

data class TrailerPreCECSharedPreferencesModel(
    var nroEquip: Int? = null,
    var idRelease: Int? = null,
)

fun TrailerPreCECSharedPreferencesModel.sharedPreferencesModelToEntity(): TrailerPreCEC {
    return with(this) {
        TrailerPreCEC(
            nroEquip = nroEquip,
            idRelease = idRelease
        )
    }
}

fun TrailerPreCEC.entityToSharedPreferencesModel(): TrailerPreCECSharedPreferencesModel {
    return with(this) {
        TrailerPreCECSharedPreferencesModel(
            nroEquip = nroEquip,
            idRelease = idRelease
        )
    }
}
