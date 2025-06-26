package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NoteMotoMecRetrofitModelOutput(
    var id: Int,
    var idHeader: Int,
    var nroOS: Int,
    var idActivity: Int,
    var idStop: Int? = null,
    val dateHour: String,
    var statusCon: Int,
)

data class NoteMotoMecRetrofitModelInput(
    val idBD: Long,
    var id: Int
)

fun NoteMotoMecRoomModel.roomModelToRetrofitModel(): NoteMotoMecRetrofitModelOutput {
    return with(this) {
        NoteMotoMecRetrofitModelOutput(
            id = this.id!!,
            idHeader = this.idHeader,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            idStop = this.idStop,
            dateHour = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(this.dateHour),
            statusCon = if (this.statusCon) 1 else 0,
        )
    }
}
