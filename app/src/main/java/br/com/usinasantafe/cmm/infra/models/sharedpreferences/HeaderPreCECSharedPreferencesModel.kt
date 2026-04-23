package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderPreCEC
import java.util.Date

data class HeaderPreCECSharedPreferencesModel(
    var dateExitMill: Date? = null,
    var dateFieldArrival: Date? = null,
    var dateExitField: Date? = null,
    var nroEquip: Long? = null,
    var regColab: Long? = null,
    var nroTurn: Int? = null,
    var idRelease: Int? = null,
)

fun HeaderPreCECSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderPreCEC {
    return with(this) {
        HeaderPreCEC(
            dateExitMill = dateExitMill,
            dateFieldArrival = dateFieldArrival,
            dateExitField = dateExitField,
            nroEquip = nroEquip,
            idRelease = idRelease,
            regColab = regColab,
            nroTurn = nroTurn,
        )
    }
}

fun HeaderPreCEC.entityToSharedPreferencesModel(): HeaderPreCECSharedPreferencesModel {
    return with(this) {
        HeaderPreCECSharedPreferencesModel(
            nroEquip = nroEquip,
            idRelease = idRelease,
            regColab = regColab,
            dateExitMill = dateExitMill,
            dateFieldArrival = dateFieldArrival,
            dateExitField = dateExitField,
            nroTurn = nroTurn,
        )
    }
}
