package br.com.usinasantafe.cmm.domain.entities.stable

data class Stop(
    val idStop: Int,
    val codStop: Int,
    val descrStop: String,
) {
    init {
        require(idStop != 0) { "The field 'idStop' cannot is null." }
        require(codStop != 0) { "The field 'codStop' cannot is null." }
    }
}
