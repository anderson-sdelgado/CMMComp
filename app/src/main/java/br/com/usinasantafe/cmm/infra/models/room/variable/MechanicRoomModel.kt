package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.Mechanic
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_MECHANIC
import java.util.Date

@Entity(tableName = TB_MECHANIC)
data class MechanicRoomModel(
    @PrimaryKey
    val id: Int? = null,
    val idHeader: Int,
    val os: Int,
    val item: Int,
    val dateHourInitial: Date = Date(),
    var dateHourFinish: Date?,
    var status: Status = Status.OPEN,
    val statusSend: StatusSend = StatusSend.SEND
)

fun Mechanic.entityToRoomModel(): MechanicRoomModel {
    return with(this) {
        MechanicRoomModel(
            id = this.id,
            idHeader = this.idHeader!!,
            os = this.nroOS!!,
            item = this.seqItem!!,
            dateHourInitial = this.dateHourInitial,
            dateHourFinish = this.dateHourFinish,
        )
    }
}
