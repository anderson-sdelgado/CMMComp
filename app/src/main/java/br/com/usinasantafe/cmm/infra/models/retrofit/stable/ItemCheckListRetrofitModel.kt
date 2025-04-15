package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList

data class ItemCheckListRetrofitModel(
    val idItemCheckList: Int,
    val idCheckList: Int,
    val descrItemCheckList: String
)

fun ItemCheckListRetrofitModel.retrofitModelToEntity(): ItemCheckList {
    return ItemCheckList(
        idItemCheckList = this.idItemCheckList,
        idCheckList = this.idCheckList,
        descrItemCheckList = this.descrItemCheckList
    )
}
