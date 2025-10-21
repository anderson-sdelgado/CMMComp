package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TB_HEADER_CHECK_LIST
import java.util.Date

@Entity(tableName = TB_HEADER_CHECK_LIST)
data class HeaderCheckListRoomModel(
    @PrimaryKey
    var id: Int? = null,
    val nroEquip: Long,
    val regOperator: Int,
    val nroTurn: Int,
    val dateHour: Date,
    val statusSend: StatusSend = StatusSend.SEND
)

fun HeaderCheckList.entityToRoomModel(): HeaderCheckListRoomModel {
    return with(this) {
        HeaderCheckListRoomModel(
            nroEquip = nroEquip,
            regOperator = regOperator,
            nroTurn = nroTurn,
            dateHour = dateHour
        )
    }
}
