package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import java.text.SimpleDateFormat
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
    var id: Int,
    val idServ: Int
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
                Locale.Builder().setLanguage("pt").setRegion("BR").build()
            ).format(this.dateHour),
            statusCon = if (this.statusCon) 1 else 0,
        )
    }
}
