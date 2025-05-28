package br.com.usinasantafe.cmm.domain.entities.stable

data class Activity(
    val idActivity: Int,
    val codActivity: Int,
    val descrActivity: String
) {
    init {
        require(idActivity != 0) { "The field 'idActivity' cannot is null." }
        require(codActivity != 0) { "The field 'codActivity' cannot is null." }
    }
}
