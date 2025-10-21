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
    val nonNullNroEquip = requireNotNull(nroEquip) { "Field 'nroEquip' cannot be null." }
    val nonNullRegOperator = requireNotNull(regOperator) { "Field 'regOperator' cannot be null." }
    val nonNullNroTurn = requireNotNull(nroTurn) { "Field 'nroTurn' cannot be null." }
    return with(this) {
        HeaderCheckList(
            nroEquip = nonNullNroEquip,
            regOperator = nonNullRegOperator,
            nroTurn = nonNullNroTurn,
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