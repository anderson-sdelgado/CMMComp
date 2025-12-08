package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusComposting
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TB_COMPOUND_COMPOSTING

@Entity(tableName = TB_COMPOUND_COMPOSTING)
data class CompoundCompostingRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var idWill: Int? = null,
    var status: Status = Status.OPEN,
    var statusSend: StatusSend = StatusSend.SEND,
    var statusComposting: StatusComposting = StatusComposting.LOAD
)
