package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.Implement

data class ImplementSharedPreferencesModel(
    val nroEquip: Long,
    val pos: Int,
)

fun ImplementSharedPreferencesModel.sharedPreferencesModelToEntity(): Implement {
    return with(this) {
        Implement(
            nroEquip = nroEquip,
            pos = pos
        )
    }
}

fun Implement.entityToSharedPreferencesModel(): ImplementSharedPreferencesModel {
    return with(this) {
        ImplementSharedPreferencesModel(
            nroEquip = nroEquip,
            pos = pos
        )
    }
}