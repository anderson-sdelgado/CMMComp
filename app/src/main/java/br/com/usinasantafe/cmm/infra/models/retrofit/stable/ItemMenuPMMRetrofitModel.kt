package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.utils.FunctionItemMenu

data class ItemMenuPMMRetrofitModel(
    val id: Int,
    val title: String,
    val function: Int
)

fun ItemMenuPMMRetrofitModel.retrofitModelToEntity(): ItemMenuPMM {
    return ItemMenuPMM(
        id = this.id,
        title = this.title,
        function = FunctionItemMenu.entries[this.function - 1]
    )
}