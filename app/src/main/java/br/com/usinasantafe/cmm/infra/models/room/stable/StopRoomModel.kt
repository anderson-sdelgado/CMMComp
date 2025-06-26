package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.utils.TB_STOP

@Entity(tableName = TB_STOP)
data class StopRoomModel (
    @PrimaryKey
    val idStop: Int,
    val codStop: Int,
    val descrStop: String
)

fun StopRoomModel.roomModelToEntity(): Stop {
    return with(this){
        Stop(
            idStop = this.idStop,
            codStop = this.codStop,
            descrStop = this.descrStop
        )
    }
}

fun Stop.entityToRoomModel(): StopRoomModel {
    return with(this){
        StopRoomModel(
            idStop = this.idStop,
            codStop = this.codStop,
            descrStop = this.descrStop
        )
    }
}
