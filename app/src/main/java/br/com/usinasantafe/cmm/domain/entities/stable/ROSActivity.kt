package br.com.usinasantafe.cmm.domain.entities.stable

data class ROSActivity(
    val idROSActivity: Int,
    val idOS: Int,
    val idActivity: Int,
) {
    init {
        require(idROSActivity != 0) { "The field 'idROSActivity' cannot is null." }
        require(idOS != 0) { "The field 'idOS' cannot is null." }
        require(idActivity != 0) { "The field 'idActivity' cannot is null." }
    }
}
