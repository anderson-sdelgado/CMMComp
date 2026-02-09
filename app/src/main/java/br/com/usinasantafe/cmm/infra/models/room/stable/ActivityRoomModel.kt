package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY

@Entity(tableName = TB_ACTIVITY)
data class ActivityRoomModel (
    @PrimaryKey
    val idActivity: Int,
    val codActivity: Int,
    val descrActivity: String
)

fun ActivityRoomModel.roomModelToEntity(): Activity {
    return with(this){
        Activity(
            idActivity = this.idActivity,
            codActivity = this.codActivity,
            descrActivity = this.descrActivity,
        )
    }
}

fun Activity.entityToRoomModel(): ActivityRoomModel {
    return with(this){
        ActivityRoomModel(
            idActivity = this.idActivity,
            codActivity = this.codActivity,
            descrActivity = this.descrActivity,
        )
    }
}