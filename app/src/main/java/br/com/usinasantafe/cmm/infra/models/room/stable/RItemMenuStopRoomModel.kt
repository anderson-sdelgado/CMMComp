package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.lib.TB_R_ITEM_MENU_STOP

@Entity(tableName = TB_R_ITEM_MENU_STOP)
data class RItemMenuStopRoomModel(
    @PrimaryKey
    val id: Int,
    val idFunction: Int,
    val idApp: Int,
    val idStop: Int
)

fun RItemMenuStopRoomModel.roomModelToEntity(): RItemMenuStop {
    return with(this){
        RItemMenuStop(
            id = this.id,
            idFunction = this.idFunction,
            idApp = this.idApp,
            idStop = this.idStop
        )
    }
}

fun RItemMenuStop.entityToRoomModel(): RItemMenuStopRoomModel {
    return with(this){
        RItemMenuStopRoomModel(
            id = this.id,
            idFunction = this.idFunction,
            idApp = this.idApp,
            idStop = this.idStop
        )
    }
}

