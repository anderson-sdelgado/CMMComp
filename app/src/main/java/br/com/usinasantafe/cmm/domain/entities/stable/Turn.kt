package br.com.usinasantafe.cmm.domain.entities.stable

data class Turn(
    val idTurn: Int,
    val codTurnEquip: Int,
    val nroTurn: Int,
    val descrTurn: String,
) {
    init {
        require(idTurn != 0) { "The field 'idTurn' cannot is null." }
        require(codTurnEquip != 0) { "The field 'codTurnEquip' cannot is null." }
        require(nroTurn != 0) { "The field 'nroTurn' cannot is null." }
    }
}

