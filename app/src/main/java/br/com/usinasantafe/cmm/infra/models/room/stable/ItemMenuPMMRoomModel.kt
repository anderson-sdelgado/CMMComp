package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.utils.TB_ITEM_MENU_PMM
import br.com.usinasantafe.cmm.utils.TypeItemMenu

@Entity(tableName = TB_ITEM_MENU_PMM)
data class ItemMenuPMMRoomModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val type: TypeItemMenu
)

fun ItemMenuPMMRoomModel.roomModelToEntity(): ItemMenuPMM {
    return with(this){
        ItemMenuPMM(
            id = this.id,
            title = this.title,
            type = this.type
        )
    }
}

fun ItemMenuPMM.entityItemMenuPMMToRoomModel(): ItemMenuPMMRoomModel {
    return with(this){
        ItemMenuPMMRoomModel(
            id = this.id,
            title = this.title,
            type = this.type
        )
    }
}