package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.utils.appList
import br.com.usinasantafe.cmm.utils.functionListPMM
import br.com.usinasantafe.cmm.utils.typeListPMM

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
        type = when(this.idApp){
            1 -> {
                typeListPMM.find { it.first == this.idType } ?: failure
            }
            2 -> failure
            3 -> failure
            4 -> failure
            else -> failure
        },
        pos = this.pos,
        function = when(this.idApp){
            1 -> {
                functionListPMM.find { it.first == this.idFunction } ?: failure
            }
            2 -> failure
            3 -> failure
            4 -> failure
            else -> failure
        },
        app = appList.find { it.first == this.idApp } ?: failure
    )
}

val failure = 0 to "FAILURE"