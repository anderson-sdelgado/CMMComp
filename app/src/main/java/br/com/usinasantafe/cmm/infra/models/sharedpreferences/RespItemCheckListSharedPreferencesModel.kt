package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList
import br.com.usinasantafe.cmm.utils.OptionRespCheckList

data class RespItemCheckListSharedPreferencesModel(
    val idItem: Int? = null,
    val option: OptionRespCheckList? = null,
)

fun RespItemCheckListSharedPreferencesModel.sharedPreferencesModelToEntity(): RespItemCheckList {
    return with(this) {
        RespItemCheckList(
            idItem = idItem,
            option = option
        )
    }
}

fun RespItemCheckList.entityToSharedPreferencesModel(): RespItemCheckListSharedPreferencesModel {
    return with(this) {
        RespItemCheckListSharedPreferencesModel(
            idItem = idItem,
            option = option
        )
    }
}
