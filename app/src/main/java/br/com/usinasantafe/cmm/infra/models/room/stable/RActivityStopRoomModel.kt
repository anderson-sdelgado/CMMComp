package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.utils.TB_R_ACTIVITY_STOP

@Entity(tableName = TB_R_ACTIVITY_STOP)
data class RActivityStopRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idRActivityStop: Int? = null,
    val idActivity: Int,
    val idStop: Int
)

fun RActivityStopRoomModel.roomModelToEntity(): RActivityStop {
    return with(this){
        RActivityStop(
            idRActivityStop = this.idRActivityStop,
            idActivity = this.idActivity,
            idStop = this.idStop
        )
    }
}

fun RActivityStop.entityRActivityStopToRoomModel(): RActivityStopRoomModel {
    return with(this){
        RActivityStopRoomModel(
            idRActivityStop = this.idRActivityStop,
            idActivity = this.idActivity,
            idStop = this.idStop
        )
    }
}
