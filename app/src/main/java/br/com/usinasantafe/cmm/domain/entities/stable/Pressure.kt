package br.com.usinasantafe.cmm.domain.entities.stable

data class Pressure(
    val id: Int,
    val idNozzle: Int,
    val value: Double,
    val speed: Int,
)
