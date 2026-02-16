package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.lib.TB_NOZZLE

@Entity(tableName = TB_NOZZLE)
data class NozzleRoomModel(
    @PrimaryKey
    val id: Int,
    val cod: Int,
    val descr: String,
)

fun NozzleRoomModel.roomModelToEntity(): Nozzle {
    return with(this){
        Nozzle(
            id = this.id,
            cod = this.cod,
            descr = this.descr
        )
    }
}

fun Nozzle.entityToRoomModel(): NozzleRoomModel {
    return with(this){
        NozzleRoomModel(
            id = this.id,
            cod = this.cod,
            descr = this.descr
        )
    }
}

