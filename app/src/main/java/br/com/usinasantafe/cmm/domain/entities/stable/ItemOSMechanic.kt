package br.com.usinasantafe.cmm.domain.entities.stable

data class ItemOSMechanic(
    val id: Int,
    val idEquip: Int,
    val nroOS: Int,
    val seqItem: Int,
    val idServ: Int,
    val idComp: Int
)
