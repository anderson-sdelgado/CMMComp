package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_HEADER_MOTO_MEC
import br.com.usinasantafe.cmm.utils.TypeEquip
import java.util.Date

@Entity(tableName = TB_HEADER_MOTO_MEC)
data class HeaderMotoMecRoomModel(
    @PrimaryKey
    var id: Int? = null,
    val regOperator: Int,
    val idEquip: Int,
    val typeEquip: TypeEquip,
    val idTurn: Int,
    val nroOS: Int,
    val idActivity: Int,
    val hourMeterInitial: Double,
    var hourMeterFinish: Double? = null,
    val dateHourInitial: Date = Date(),
    var dateHourFinish: Date? = null,
    var statusCon: Boolean,
    var statusSend: StatusSend = StatusSend.SEND,
    var status: Status = Status.OPEN,
    var idBD: Long? = null
)

fun HeaderMotoMec.entityToRoomModel(): HeaderMotoMecRoomModel {
    return with(this) {
        HeaderMotoMecRoomModel(
            id = this.id,
            regOperator = this.regOperator!!,
            idEquip = this.idEquip!!,
            typeEquip = this.typeEquip!!,
            idTurn = this.idTurn!!,
            nroOS = this.nroOS!!,
            idActivity = this.idActivity!!,
            hourMeterInitial = this.hourMeter!!,
            statusCon = this.statusCon
        )
    }
}