package br.com.usinasantafe.cmm.domain.repositories.variable

interface HeaderMotoMecRepository {
    suspend fun setRegOperator(regOperator: Int): Result<Boolean>
    suspend fun setIdEquip(idEquip: Int): Result<Boolean>
    suspend fun setIdTurn(idTurn: Int): Result<Boolean>
    suspend fun setNroOS(nroOS: Int): Result<Boolean>
}