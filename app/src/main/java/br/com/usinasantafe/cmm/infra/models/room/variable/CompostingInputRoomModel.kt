package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusComposting
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_COMPOSTING_INPUT

@Entity(tableName = TB_COMPOSTING_INPUT)
data class CompostingInputRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var status: Status = Status.OPEN,
    var statusSend: StatusSend = StatusSend.SEND,
    val statusComposting: StatusComposting = StatusComposting.LOAD
)

