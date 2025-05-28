package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_HEADER_MOTO_MEC
import java.util.Date

@Entity(tableName = TB_HEADER_MOTO_MEC)
data class HeaderMotoMecRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var regOperator: Int,
    var idEquip: Int,
    var idTurn: Int,
    var nroOS: Int,
    var idActivity: Int,
    var measureInitial: Double,
    val dateHourInitial: Date = Date(),
    val statusSend: StatusSend = StatusSend.SEND,
    val status: Status = Status.OPEN
)

fun HeaderMotoMecRoomModel.roomModelToEntity(): HeaderMotoMec {
    return with(this) {
        HeaderMotoMec(
            id = this.id,
            regOperator = this.regOperator,
            idEquip = this.idEquip,
            idTurn = this.idTurn,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            measureInitial = this.measureInitial
        )
    }
}

fun HeaderMotoMec.entityToRoomModel(): HeaderMotoMecRoomModel {
    return with(this) {
        HeaderMotoMecRoomModel(
            id = this.id,
            regOperator = this.regOperator!!,
            idEquip = this.idEquip!!,
            idTurn = this.idTurn!!,
            nroOS = this.nroOS!!,
            idActivity = this.idActivity!!,
            measureInitial = this.measureInitial!!
        )
    }
}