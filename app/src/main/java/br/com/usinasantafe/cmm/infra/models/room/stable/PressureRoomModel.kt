package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.lib.TB_PRESSURE

@Entity(tableName = TB_PRESSURE)
data class PressureRoomModel(
    @PrimaryKey
    val id: Int,
    val idNozzle: Int,
    val value: Double,
    val speed: Int,
)

fun PressureRoomModel.roomModelToEntity(): Pressure {
    return with(this){
        Pressure(
            id = this.id,
            idNozzle = this.idNozzle,
            value = this.value,
            speed = this.speed,
        )
    }
}

fun Pressure.entityToRoomModel(): PressureRoomModel {
    return with(this){
        PressureRoomModel(
            id = this.id,
            idNozzle = this.idNozzle,
            value = this.value,
            speed = this.speed,
        )
    }
}



