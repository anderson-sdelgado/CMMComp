package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import java.util.Date

data class HeaderCheckListSharedPreferencesModel(
    val nroEquip: Long? = null,
    val regOperator: Int? = null,
    val nroTurn: Int? = null,
    val dateHour: Date = Date()
)

fun HeaderCheckListSharedPreferencesModel.sharedPreferencesModelToEntity(): HeaderCheckList {
    return with(this) {
        HeaderCheckList(
            nroEquip = nroEquip!!,
            regOperator = regOperator!!,
            nroTurn = nroTurn!!,
            dateHour = dateHour
        )
    }
}

fun HeaderCheckList.entityToSharedPreferencesModel(): HeaderCheckListSharedPreferencesModel {
    return with(this) {
        HeaderCheckListSharedPreferencesModel(
            nroEquip = nroEquip,
            regOperator = regOperator,
            nroTurn = nroTurn,
            dateHour = dateHour
        )
    }
}