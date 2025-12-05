package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import java.util.Date

data class PreCECSharedPreferencesModel(
    var nroEquip: Int? = null,
    var libEquip: Int? = null,
    var regColab: Long? = null,
    var nroTrailer1: Int? = null,
    var libTrailer1: Int? = null,
    var nroTrailer2: Int? = null,
    var libTrailer2: Int? = null,
    var nroTrailer3: Int? = null,
    var libTrailer3: Int? = null,
    var nroTrailer4: Int? = null,
    var libTrailer4: Int? = null,
    var dateExitMill: Date? = null,
    var dateFieldArrival: Date? = null,
    var dateExitField: Date? = null,
    var nroTurn: Int? = null
)

fun PreCECSharedPreferencesModel.sharedPreferencesModelToEntity(): PreCEC {
    return with(this) {
        PreCEC(
            nroEquip = nroEquip,
            libEquip = libEquip,
            regColab = regColab,
            nroTrailer1 = nroTrailer1,
            libTrailer1 = libTrailer1,
            nroTrailer2 = nroTrailer2,
            libTrailer2 = libTrailer2,
            nroTrailer3 = nroTrailer3,
            libTrailer3 = libTrailer3,
            nroTrailer4 = nroTrailer4,
            libTrailer4 = libTrailer4,
            dateExitMill = dateExitMill,
            dateFieldArrival = dateFieldArrival,
            dateExitField = dateExitField,
            nroTurn = nroTurn
        )
    }
}

fun PreCEC.entityToSharedPreferencesModel(): PreCECSharedPreferencesModel {
    return with(this) {
        PreCECSharedPreferencesModel(
            nroEquip = nroEquip,
            libEquip = libEquip,
            regColab = regColab,
            nroTrailer1 = nroTrailer1,
            libTrailer1 = libTrailer1,
            nroTrailer2 = nroTrailer2,
            libTrailer2 = libTrailer2,
            nroTrailer3 = nroTrailer3,
            libTrailer3 = libTrailer3,
            nroTrailer4 = nroTrailer4,
            libTrailer4 = libTrailer4,
            dateExitMill = dateExitMill,
            dateFieldArrival = dateFieldArrival,
            dateExitField = dateExitField,
            nroTurn = nroTurn
        )
    }
}
