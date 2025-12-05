package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.ItemRespCheckList
import br.com.usinasantafe.cmm.lib.OptionRespCheckList

data class RespItemCheckListSharedPreferencesModel(
    val idItem: Int,
    val option: OptionRespCheckList,
)

fun RespItemCheckListSharedPreferencesModel.sharedPreferencesModelToEntity(): ItemRespCheckList {
    return with(this) {
        ItemRespCheckList(
            idItem = idItem,
            option = option
        )
    }
}

fun ItemRespCheckList.entityToSharedPreferencesModel(): RespItemCheckListSharedPreferencesModel {
    return with(this) {
        RespItemCheckListSharedPreferencesModel(
            idItem = idItem,
            option = option
        )
    }
}
