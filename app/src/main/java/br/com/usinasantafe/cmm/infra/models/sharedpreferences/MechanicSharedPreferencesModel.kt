package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.Mechanic


data class MechanicSharedPreferencesModel(
    var nroOS: Int? = null,
    var seqItem: Int? = null,
)

fun MechanicSharedPreferencesModel.sharedPreferencesModelToEntity(): Mechanic {
    return with(this) {
        Mechanic(
            nroOS = nroOS,
            seqItem = seqItem
        )
    }
}

fun Mechanic.entityToSharedPreferencesModel(): MechanicSharedPreferencesModel {
    return with(this) {
        MechanicSharedPreferencesModel(
            nroOS = nroOS,
            seqItem = seqItem
        )
    }
}