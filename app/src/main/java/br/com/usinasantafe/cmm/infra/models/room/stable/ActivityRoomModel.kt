package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY

@Entity(tableName = TB_ACTIVITY)
data class ActivityRoomModel (
    @PrimaryKey
    val id: Int,
    val cod: Int,
    val descr: String
)

fun ActivityRoomModel.roomModelToEntity(): Activity {
    return with(this){
        Activity(
            id = this.id,
            cod = this.cod,
            descr = this.descr,
        )
    }
}

fun Activity.entityToRoomModel(): ActivityRoomModel {
    return with(this){
        ActivityRoomModel(
            id = this.id,
            cod = this.cod,
            descr = this.descr,
        )
    }
}