package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Service
import br.com.usinasantafe.cmm.lib.TB_SERVICE

@Entity(tableName = TB_SERVICE)
data class ServiceRoomModel(
    @PrimaryKey
    val id: Int,
    val cod: Int,
    val descr: String
)

fun ServiceRoomModel.roomModelToEntity(): Service {
    return with(this) {
        Service(
            id = this.id,
            cod = this.cod,
            descr = this.descr,
        )
    }
}

fun Service.entityToRoomModel(): ServiceRoomModel {
    return with(this) {
        ServiceRoomModel(
            id = this.id,
            cod = this.cod,
            descr = this.descr,
        )
    }
}
