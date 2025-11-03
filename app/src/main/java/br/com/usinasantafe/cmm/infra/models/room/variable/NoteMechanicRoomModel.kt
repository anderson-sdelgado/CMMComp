package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.NoteMechanic
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_NOTE_MECHANIC
import java.util.Date

@Entity(tableName = TB_NOTE_MECHANIC)
data class NoteMechanicRoomModel(
    @PrimaryKey
    val id: Int? = null,
    val idHeader: Int,
    val os: Int,
    val seq: Int,
    val dateHourInitial: Date = Date(),
    val dateHourFinish: Date?,
    val status: Status = Status.OPEN,
    val statusSend: StatusSend = StatusSend.SEND
)

fun NoteMechanic.entityToRoomModel(): NoteMechanicRoomModel {
    return with(this) {
        NoteMechanicRoomModel(
            id = this.id,
            idHeader = this.idHeader!!,
            os = this.os!!,
            seq = this.seq!!,
            dateHourInitial = this.dateHourInitial,
            dateHourFinish = this.dateHourFinish,
        )
    }
}
