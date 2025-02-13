package br.com.usinasantafe.cmm.domain.entities.stable

data class Equip(
    val idEquip: Int,
    val nroEquip: Long,
    val codClasse: Int,
    val descrClasse: String,
    val codTurno: Int,
    val idCheckList: Int,
    val tipoFert: Int,
    val horimetro: Double,
    val medicao: Double,
    val tipo: Int,
    val classif: Int,
    val flagApontMecan: Boolean,
    val flagApontPneu: Boolean,
)
