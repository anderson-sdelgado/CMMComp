package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.NoteMotoMec

data class NoteMotoMecSharedPreferencesModel(
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    var statusCon: Boolean? = null,
)

fun NoteMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): NoteMotoMec {
    return with(this) {
        NoteMotoMec(
            nroOS = nroOS,
            idActivity = idActivity,
            idStop = idStop,
            statusCon = statusCon
        )
    }
}

fun NoteMotoMec.entityToSharedPreferencesModel(): NoteMotoMecSharedPreferencesModel {
    return with(this) {
        NoteMotoMecSharedPreferencesModel(
            nroOS = nroOS,
            idActivity = idActivity,
            idStop = idStop,
            statusCon = statusCon
        )
    }
}