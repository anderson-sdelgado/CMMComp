package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.NoteMotoMec
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_NOTE_MOTO_MEC
import java.util.Date


@Entity(tableName = TB_NOTE_MOTO_MEC)
data class NoteMotoMecRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var idHeader: Int,
    var nroOS: Int,
    var idActivity: Int,
    var idStop: Int? = null,
    val dateHour: Date = Date(),
    val statusSend: StatusSend = StatusSend.SEND,
    val status: Status = Status.CLOSE
)

fun NoteMotoMecRoomModel.roomModelToEntity(): NoteMotoMec {
    return with(this){
        NoteMotoMec(
            id = this.id,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            idStop = this.idStop
        )
    }
}

fun NoteMotoMec.entityToRoomModel(
    idHeader: Int
): NoteMotoMecRoomModel{
    return with(this){
        NoteMotoMecRoomModel(
            id = this.id,
            idHeader = idHeader,
            nroOS = this.nroOS!!,
            idActivity = this.idActivity!!,
            idStop = this.idStop
        )
    }
}
