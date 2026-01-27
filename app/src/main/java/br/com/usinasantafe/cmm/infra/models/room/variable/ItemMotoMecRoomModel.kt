package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_ITEM_MOTO_MEC
import br.com.usinasantafe.cmm.utils.required
import java.util.Date


@Entity(tableName = TB_ITEM_MOTO_MEC)
data class ItemMotoMecRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var idHeader: Int,
    var nroOS: Int,
    var idActivity: Int,
    var idStop: Int? = null,
    val dateHour: Date = Date(),
    var statusSend: StatusSend = StatusSend.SEND,
    val status: Status = Status.CLOSE,
    val statusCon: Boolean = false,
    var idServ: Int? = null,
    val nroEquipTranshipment: Long? = null,
)

fun ItemMotoMec.entityToRoomModel(
    idHeader: Int
): ItemMotoMecRoomModel{
    return with(this){
        ItemMotoMecRoomModel(
            id = this.id,
            idHeader = idHeader,
            nroOS = this.nroOS.required("nroOS"),
            idActivity = this.idActivity.required("idActivity"),
            idStop = this.idStop,
            statusCon = this.statusCon.required("statusCon"),
            dateHour = dateHour,
            nroEquipTranshipment = this.nroEquipTranshipment
        )
    }
}

fun ItemMotoMecRoomModel.roomModelToEntity(): ItemMotoMec {
    return with(this){
        ItemMotoMec(
            id = this.id,
            nroOS = this.nroOS,
            idActivity = this.idActivity,
            idStop = this.idStop,
            statusCon = this.statusCon,
            dateHour = this.dateHour,
            nroEquipTranshipment = this.nroEquipTranshipment
        )
    }
}
