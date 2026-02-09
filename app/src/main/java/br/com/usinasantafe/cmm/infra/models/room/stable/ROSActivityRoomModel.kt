package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.lib.TB_R_OS_ACTIVITY

@Entity(tableName = TB_R_OS_ACTIVITY)
data class ROSActivityRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idROSActivity: Int? = null,
    val idOS: Int,
    val idActivity: Int
)

fun ROSActivityRoomModel.roomModelToEntity(): ROSActivity {
    return with(this){
        ROSActivity(
            idROSActivity = this.idROSActivity,
            idOS = this.idOS,
            idActivity = this.idActivity
        )
    }
}

fun ROSActivity.entityToRoomModel(): ROSActivityRoomModel {
    return with(this){
        ROSActivityRoomModel(
            idROSActivity = this.idROSActivity,
            idOS = this.idOS,
            idActivity = this.idActivity
        )
    }
}
