package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.Implement
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_IMPLEMENT
import java.util.Date

@Entity(tableName = TB_IMPLEMENT)
data class ImplementRoomModel(
    @PrimaryKey
    var id: Int? = null,
    val idItem: Int,
    val nroEquip: Long,
    val pos: Int,
    val dateHour: Date = Date(),
    var statusSend: StatusSend = StatusSend.SEND
)

fun Implement.entityToRoomModel(idItem: Int): ImplementRoomModel {
    return with(this) {
        ImplementRoomModel(
            idItem = idItem,
            nroEquip = nroEquip,
            pos = pos
        )
    }
}

