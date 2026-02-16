package br.com.usinasantafe.cmm.domain.entities.stable

data class Activity(
    val id: Int,
    val cod: Int,
    val descr: String
) {
    init {
        require(id != 0) { "The field 'idActivity' cannot is null." }
        require(cod != 0) { "The field 'codActivity' cannot is null." }
    }
}
