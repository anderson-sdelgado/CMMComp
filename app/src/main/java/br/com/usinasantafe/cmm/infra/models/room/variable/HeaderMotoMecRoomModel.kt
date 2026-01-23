package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_HEADER_MOTO_MEC
import br.com.usinasantafe.cmm.lib.TypeEquip
import java.util.Date
import kotlin.Int

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
    val flowComposting: FlowComposting? = null,
    val hourMeterInitial: Double,
    var hourMeterFinish: Double? = null,
    val dateHourInitial: Date = Date(),
    var dateHourFinish: Date? = null,
    var statusCon: Boolean,
    var statusSend: StatusSend = StatusSend.SEND,
    var status: Status = Status.OPEN,
    var idServ: Int? = null,
    val idEquipMotorPump: Int? = null,
)

fun HeaderMotoMec.entityToRoomModel(): HeaderMotoMecRoomModel {
    fun <T> T?.required(field: String): T =
        this ?: throw NullPointerException("$field is required")

    return HeaderMotoMecRoomModel(
        id = id,
        regOperator = regOperator.required("regOperator"),
        idEquip = idEquip.required("idEquip"),
        typeEquip = typeEquip.required("typeEquip"),
        idTurn = idTurn.required("idTurn"),
        nroOS = nroOS.required("nroOS"),
        idActivity = idActivity.required("idActivity"),
        hourMeterInitial = hourMeter.required("hourMeter"),
        flowComposting = flowComposting,
        statusCon = statusCon.required("statusCon"),
        idEquipMotorPump = idEquipMotorPump
    )
}

fun HeaderMotoMecRoomModel.roomModelToSharedPreferences(): HeaderMotoMecSharedPreferencesModel {
    return with(this) {
        HeaderMotoMecSharedPreferencesModel(
            id = this.id,
            regOperator = this.regOperator,
            idEquip = this.idEquip,
            typeEquip = this.typeEquip,
            idTurn = this.idTurn,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            statusCon = this.statusCon
        )
    }
}