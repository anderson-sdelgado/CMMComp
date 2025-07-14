package br.com.usinasantafe.cmm.infra.models.internal

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMenu
import br.com.usinasantafe.cmm.utils.TypeItemMenu

data class ItemMenuInternalModel(
    val id: Int,
    val title: String,
    val type: Int,
)

fun ItemMenuInternalModel.internalModelToEntity(): ItemMenu {
    return with(this) {
        ItemMenu(
            id = this.id,
            title = this.title,
            type = TypeItemMenu.entries[this.type - 1]
        )
    }
}

fun ItemMenu.entityToInternalModel(): ItemMenuInternalModel {
    return with(this) {
        ItemMenuInternalModel(
            id = this.id,
            title = this.title,
            type = this.type.ordinal
        )
    }
}

