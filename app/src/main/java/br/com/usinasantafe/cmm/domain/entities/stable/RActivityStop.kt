package br.com.usinasantafe.cmm.domain.entities.stable

data class RActivityStop(
    val idRActivityStop: Int? = null,
    val idActivity: Int,
    val idStop: Int,
) {
    init {
        require(idStop != 0) { "The field 'idStop' cannot is null." }
        require(idActivity != 0) { "The field 'idActivity' cannot is null." }
    }
}
