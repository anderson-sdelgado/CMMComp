package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.utils.TB_ITEM_MENU

@Entity(tableName = TB_ITEM_MENU)
data class ItemMenuRoomModel(
    @PrimaryKey
    val id: Int,
    val descr: String,
    val idType: Int,
    val pos: Int,
    val idFunction: Int,
    val idApp: Int,
)

fun ItemMenuRoomModel.roomModelToEntity(): ItemMenu {
    return with(this){
        ItemMenu(
            id = this.id,
            descr = this.descr,
            idType = this.idType,
            pos = this.pos,
            idFunction = this.idFunction,
            idApp = this.idApp,
        )
    }
}

fun ItemMenu.entityItemMenuToRoomModel(): ItemMenuRoomModel {
    return with(this){
        ItemMenuRoomModel(
            id = this.id,
            descr = this.descr,
            idType = this.idType,
            pos = this.pos,
            idFunction = this.idFunction,
            idApp = this.idApp,
        )
    }
}