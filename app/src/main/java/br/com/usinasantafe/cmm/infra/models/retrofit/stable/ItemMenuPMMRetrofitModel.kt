package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.TypeStop

data class ItemMenuPMMRetrofitModel(
    val id: Int,
    val title: String,
    val type: Int
)

fun ItemMenuPMMRetrofitModel.retrofitModelToEntity(): ItemMenuPMM {
    return ItemMenuPMM(
        id = this.id,
        title = this.title,
        type = TypeItemMenu.entries[this.type - 1]
    )
}