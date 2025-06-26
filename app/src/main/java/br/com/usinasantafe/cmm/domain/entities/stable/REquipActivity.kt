package br.com.usinasantafe.cmm.domain.entities.stable

data class REquipActivity(
    val idREquipActivity: Int? = null,
    val idEquip: Int,
    val idActivity: Int
) {
    init {
        require(idEquip != 0) { "The field 'idEquip' cannot is null." }
        require(idActivity != 0) { "The field 'idActivity' cannot is null." }
    }
}
