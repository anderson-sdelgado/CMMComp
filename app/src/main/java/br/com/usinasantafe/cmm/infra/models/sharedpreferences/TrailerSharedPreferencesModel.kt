package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.Implement

data class TrailerSharedPreferencesModel(
    var idEquip: Int,
    var pos: Int,
)

fun TrailerSharedPreferencesModel.sharedPreferencesModelToEntity(): Implement {
    return Implement(
        idEquip = this.idEquip,
        pos = this.pos,
    )
}

fun Implement.entityToTrailerSharedPreferencesModel(): TrailerSharedPreferencesModel {
    return TrailerSharedPreferencesModel(
        idEquip = this.idEquip!!,
        pos = this.pos!!,
    )
}