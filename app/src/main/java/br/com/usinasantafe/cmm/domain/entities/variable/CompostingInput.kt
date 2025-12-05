package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusComposting
import br.com.usinasantafe.cmm.lib.StatusSend

data class CompostingInput(
    var status: Status = Status.OPEN,
    var statusSend: StatusSend = StatusSend.SEND,
    val statusComposting: StatusComposting = StatusComposting.LOAD
)
