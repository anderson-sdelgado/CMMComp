package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.utils.StatusSend

data class NoteMotoMec(
    var id: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    var statusCon: Boolean? = true
)