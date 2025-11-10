package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.failure
import br.com.usinasantafe.cmm.utils.TB_ITEM_MENU
import br.com.usinasantafe.cmm.utils.appList
import br.com.usinasantafe.cmm.utils.functionListPMM
import br.com.usinasantafe.cmm.utils.typeListPMM

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
            app = appList.find { it.first == this.idApp } ?: failure,
        )
    }
}

fun ItemMenu.entityItemMenuToRoomModel(): ItemMenuRoomModel {
    return with(this){
        ItemMenuRoomModel(
            id = this.id,
            descr = this.descr,
            idType = this.type.first,
            pos = this.pos,
            idFunction = this.function.first,
            idApp = this.app.first,
        )
    }
}

val failure = 0 to "FAILURE"