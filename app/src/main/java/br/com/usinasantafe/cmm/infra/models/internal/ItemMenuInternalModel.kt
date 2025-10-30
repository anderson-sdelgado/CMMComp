package br.com.usinasantafe.cmm.infra.models.internal

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.utils.FunctionItemMenu

data class ItemMenuInternalModel(
    val id: Int,
    val title: String,
    val type: Int,
)

fun ItemMenuInternalModel.internalModelToEntity(): ItemMenuPMM {
    return with(this) {
        ItemMenuPMM(
            id = this.id,
            title = this.title,
            function = FunctionItemMenu.entries[this.type - 1]
        )
    }
}

fun ItemMenuPMM.entityToInternalModel(): ItemMenuInternalModel {
    return with(this) {
        ItemMenuInternalModel(
            id = this.id,
            title = this.title,
            type = this.function.ordinal
        )
    }
}

