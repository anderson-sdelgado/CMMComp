package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class HeaderMotoMecRetrofitModelOutput(
    val id: Int,
    val regOperator: Int,
    val idEquip: Int,
    val typeEquip: Int,
    val idTurn: Int,
    val nroOS: Int,
    val idActivity: Int,
    val hourMeterInitial: Double,
    var hourMeterFinish: Double? = null,
    val dateHourInitial: String,
    var dateHourFinish: String? = null,
    var number: Long? = null,
    var status: Int,
    var statusCon: Int,
    var noteMotoMecList: List<NoteMotoMecRetrofitModelOutput>
)

data class HeaderMotoMecRetrofitModelInput(
    val idBD: Long,
    val id: Int,
    val noteMotoMecList: List<NoteMotoMecRetrofitModelInput>
)

fun HeaderMotoMecRoomModel.roomModelToRetrofitModel(
    number: Long,
    noteMotoMecList: List<NoteMotoMecRetrofitModelOutput>
): HeaderMotoMecRetrofitModelOutput {
    return with(this) {
        HeaderMotoMecRetrofitModelOutput(
            id = this.id!!,
            regOperator = this.regOperator,
            idEquip = this.idEquip,
            typeEquip = this.typeEquip.ordinal + 1,
            idTurn = this.idTurn,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            hourMeterInitial = this.hourMeterInitial,
            hourMeterFinish = this.hourMeterFinish,
            dateHourInitial = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dateHourInitial),
            dateHourFinish = if(this.dateHourFinish == null) null else SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                this.dateHourFinish!!
            ),
            status = this.status.ordinal + 1,
            statusCon = if (this.statusCon) 1 else 0,
            number = number,
            noteMotoMecList = noteMotoMecList
        )
    }
}

