package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.HeaderMotoMec
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_HEADER_MOTO_MEC
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import java.util.Date
import kotlin.Int

@Entity(tableName = TB_HEADER_MOTO_MEC)
data class HeaderMotoMecRoomModel(
    @PrimaryKey
    var id: Int? = null,
    val regOperator: Int,
    val idEquip: Int,
    val typeEquipMain: TypeEquipMain,
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
    var idServ: Int? = null
)

fun HeaderMotoMec.entityToRoomModel(): HeaderMotoMecRoomModel {
    return with(this) {
        HeaderMotoMecRoomModel(
            id = this.id,
            regOperator = this.regOperator!!,
            idEquip = this.idEquip!!,
            typeEquipMain = this.typeEquipMain!!,
            idTurn = this.idTurn!!,
            nroOS = this.nroOS!!,
            idActivity = this.idActivity!!,
            hourMeterInitial = this.hourMeter!!,
            flowComposting = this.flowComposting,
            statusCon = this.statusCon!!
        )
    }
}

fun HeaderMotoMecRoomModel.roomModelToSharedPreferences(): HeaderMotoMecSharedPreferencesModel {
    return with(this) {
        HeaderMotoMecSharedPreferencesModel(
            id = this.id,
            regOperator = this.regOperator,
            idEquip = this.idEquip,
            typeEquipMain = this.typeEquipMain,
            idTurn = this.idTurn,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            statusCon = this.statusCon
        )
    }
}