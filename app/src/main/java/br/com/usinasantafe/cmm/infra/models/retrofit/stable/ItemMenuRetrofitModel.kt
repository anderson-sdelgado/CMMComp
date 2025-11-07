package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu

data class ItemMenuRetrofitModel(
    val id: Int,
    val descr: String,
    val idType: Int,
    val pos: Int,
    val idFunction: Int,
    val idApp: Int
)

fun ItemMenuRetrofitModel.retrofitModelToEntity(): ItemMenu {
    return ItemMenu(
        id = this.id,
        descr = this.descr,
        idType = this.idType,
        pos = this.pos,
        idFunction = this.idFunction,
        idApp = this.idApp
    )
}