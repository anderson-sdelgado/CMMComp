package br.com.usinasantafe.cmm.domain.entities.variable

import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquipMain

data class HeaderMotoMec(
    var id: Int? = null,
    var regOperator: Int? = null,
    var idEquip: Int? = null,
    var typeEquipMain: TypeEquipMain? = null,
    var idTurn: Int? = null,
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var hourMeter: Double? = null,
    var statusCon: Boolean = true,
    var flowComposting: FlowComposting? = null
)
