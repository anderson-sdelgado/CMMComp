package br.com.usinasantafe.cmm.domain.entities.stable

data class Colab(
    val regColab: Long,
    val nameColab: String,
) {
    init {
        require(regColab != 0L) { "The field 'regColab' cannot is null." }
    }
}
