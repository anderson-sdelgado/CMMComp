package br.com.usinasantafe.cmm.domain.repositories.variable

interface HeaderMotoMecRepository {
    suspend fun setRegOperator(regOperator: Int): Result<Boolean>
    suspend fun setIdEquip(idEquip: Int): Result<Boolean>
    suspend fun setIdTurn(idTurn: Int): Result<Boolean>
    suspend fun setNroOS(nroOS: Int): Result<Boolean>
    suspend fun getNroOS(): Result<Int>
    suspend fun setIdActivity(idActivity: Int): Result<Boolean>
    suspend fun getIdEquip(): Result<Int>
    suspend fun setMeasureInitial(measure: Double): Result<Boolean>
    suspend fun save(): Result<Boolean>
    suspend fun checkHeaderOpen(): Result<Boolean>
    suspend fun getIdByHeaderOpen(): Result<Int>
}